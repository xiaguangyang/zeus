package com.zeus.integration.spring.web.action;


import com.zeus.data.api.*;
import com.zeus.data.api.exception.BusinessException;
import com.zeus.data.api.model.Page;
import com.zeus.data.api.model.Pagination;
import com.zeus.data.api.model.QPageQuery;
import com.zeus.data.api.model.Query;
import com.zeus.data.api.session.Session;
import com.zeus.integration.spring.web.vo.Dictionary;
import com.zeus.integration.spring.web.vo.PageResponse;
import com.zeus.integration.spring.web.vo.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 基础控制器.
 *
 * @author quweixin
 * @since
 */
public abstract class ActionSupport<M, Q extends Query, S> {
    private static final Logger logger = LoggerFactory.getLogger(ActionSupport.class);
    public static final String APPLICATION_ZIP = "application/x-zip-compressed;charset=utf-8";
    public static final String OCTETS_STREAM = "octets/stream";
    @Inject
    protected Session session;
    //业务处理对象
    @Inject
    protected S service;
    //模块名称
    protected String module;

    @RequestMapping("")
    public ModelAndView index(final @ModelAttribute ModelAndView view) {
        view.setViewName(getModule() + "_manage");
        return view;
    }

    /**
     * 构建查询条件
     *
     * @param query 页面传递的查询条件
     * @return 查询条件
     */
    protected Q buildQuery(Q query) {
        // 当前用户权限
//        if (query != null && query instanceof QSession) {
//            UserDetail userDetail = session.getUser();
//            QSession qSession = (QSession) query;
//            qSession.setRoles(userDetail.getRoles());
//            if (!UserDetails.isAdmin(userDetail)) {
//                // 不是管理员用户则绑定上用户ID
//                qSession.setUserId(userDetail.getId());
//            }
//        }
        return query;
    }

    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    protected PageResponse find(Q query, Pagination pagination) {
        //构建查询对象
        final QPageQuery pageQuery = new QPageQuery(pagination, buildQuery(query));
        final PageResponse response = new PageResponse(pagination, query);
        execute(response, null, new Executor() {
            @Override
            public void execute() throws Exception {
                //获取查询结果
                Page page = ((Pageable) service).findByQuery(pageQuery);
                //设置响应对象
                if (page != null) {
                    response.setResult(page.getResult());
                    response.setPagination(page.getPagination());
                }
            }
        });
        return response;
    }

    /**
     * 根据应用编码模糊查询执行条数的应用列表
     *
     * @param query 查询条件
     * @param limit 　查询条数
     * @return 匹配的应用列表
     */
    protected List<Dictionary> find(Query query, int limit, DictionaryConverter<M> converter) {
        // 查询条件
        Pagination pagination = new Pagination();
        if (limit > 0) {
            pagination.setPageSize(limit);
        }
        QPageQuery pageQuery = new QPageQuery(pagination, query);

        // 查询数据
        Page<M> page = ((Pageable) service).findByQuery(pageQuery);

        // 键值对
        List<Dictionary> result = new ArrayList<Dictionary>();
        if (page != null && page.getResult() != null) {
            for (M model : page.getResult()) {
                result.add(converter.convert(model));
            }
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/search/{id}")
    protected Response findById(@PathVariable("id") long id) {
        //获取查询结果
        M m = (M) ((Identifiable) service).findById(id);
        //设置响应对象
        return Response.build(m).success();
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    protected ModelAndView add(@ModelAttribute ModelAndView mv) {
        mv.setViewName(getModule() + "_add");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    protected Response add(M model) {
        try {
            ((Addable) service).add(model);
            return Response.build().success();
        } catch (Exception e) {
            return onException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    protected Response delete(@ModelAttribute M model) {
        try {
            ((Deletable) service).delete(model);
            return Response.build().success();
        } catch (Exception e) {
            return onException(e);
        }
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    protected ModelAndView update(final @PathVariable("id") long id, final @ModelAttribute ModelAndView view) {
        final Response response = new Response();
        execute(response, view, new Executor() {
            @Override
            public void execute() throws Exception {
                M result = (M) ((Identifiable) service).findById(id);
                response.setResult(result);
                view.addObject("response", response);
                view.setViewName(getModule() + "_edit");
            }
        });
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "update", method = RequestMethod.POST)
    protected Response update(@ModelAttribute M model) {
        try {
            ((Updatable) service).update(model);
            return Response.build().success();
        } catch (Exception e) {
            return onException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    protected Response enable(@ModelAttribute M model) {
        try {
            ((Lifeable) service).enable(model);
            return Response.build().success();
        } catch (Exception e) {
            return onException(e);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    protected Response disable(@ModelAttribute M model) {
        try {
            ((Lifeable) service).disable(model);
            return Response.build().success();
        } catch (Exception e) {
            return onException(e);
        }
    }

    /**
     * 压缩
     *
     * @param in   输入流
     * @param out  输出流
     * @param name 名称
     * @throws IOException
     */
    protected void compress(final InputStream in, final OutputStream out, final String name) throws IOException {
        ZipOutputStream zout = new ZipOutputStream(out);
        try {
            zout.putNextEntry(new ZipEntry(name));
            byte[] buffer = new byte[1024 * 4];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                zout.write(buffer, 0, len);
            }
            zout.closeEntry();
        } finally {
//            Closeables.close(zout);
        }
    }

    /**
     * 压缩
     *
     * @param buffer 输入流
     * @param out    输出流
     * @param name   名称
     * @throws IOException
     */
    protected void compress(final byte[] buffer, final OutputStream out, final String name) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        try {
            compress(in, out, name);
        } finally {
//            Closeables.close(in);
        }
    }


    /**
     * 压缩
     *
     * @param content 文本
     * @param name    名称
     * @throws IOException
     */
    protected byte[] compress(final String content, final String charset, final String name) throws IOException {
        byte[] buffer = content.getBytes(charset);
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        ByteArrayOutputStream out = new ByteArrayOutputStream(buffer.length);
        try {
            compress(in, out, name);
            return out.toByteArray();
        } finally {
//            Closeables.close(in);
//            Closeables.close(out);
        }
    }

    /**
     * 导出数据流
     *
     * @param response    应答
     * @param name        名称
     * @param contentType 类型
     * @param in          输入流
     * @param length      长度
     * @throws IOException
     */
    protected void download(final HttpServletResponse response, final String name, final String contentType,
                            final InputStream in, final long length) throws IOException {
        response.reset();
        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader("Content-Disposition",
                "attachment;filename=" + new String(name.getBytes("utf-8"), "iso8859-1"));
        response.addHeader("Content-Length", String.valueOf(length));
        response.setContentType(contentType);

        OutputStream os = response.getOutputStream();
//        IOUtil.copy(in, os, length);
        os.flush();
    }

    /**
     * 导出文件
     *
     * @param response    应答
     * @param name        名称
     * @param contentType 类型
     * @param file        文件
     * @throws IOException
     */
    protected void download(final HttpServletResponse response, final String name, final String contentType,
                            final File file) throws IOException {
        // 文件不存在时直接返回
        if (!file.exists()) {
            return;
        }
        InputStream fis = new FileInputStream(file);
        try {
            download(response, name, contentType, fis, file.length());
        } finally {
//            Closeables.close(fis);
        }
    }

    /**
     * 获取随机文件
     *
     * @param root      根路径，如果为空，则为资源根路径
     * @param path      子路径
     * @param extension 扩展名
     * @return 文件
     */
    protected File randomFile(final String root, final String path, final String extension) {
        if (extension == null || extension.isEmpty()) {
            throw new IllegalArgumentException("extension can not be empty.");
        }
        // 文件名
        String fileName = UUID.randomUUID().toString() + extension;
        // 根路径
        String filePath = root;
        if (root == null || root.isEmpty()) {
            filePath = this.getClass().getClassLoader().getResource("/").getPath();
        }
        // 路径
        File file = new File(filePath);
        if (path != null && !path.isEmpty()) {
            file = new File(file, path);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        return new File(file, fileName);
    }

    /**
     * 获取模块名称
     *
     * @return 模块
     */
    protected String getModule() {
        if (module == null) {
            Type genType = getClass().getGenericSuperclass();
            Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
            module = ((Class) params[0]).getSimpleName().toLowerCase();
        }
        return module;
    }

    //处理未捕获的异常
    @ExceptionHandler
    @ResponseBody
    protected Response onException(Exception e) {
        if (!(e instanceof BusinessException)) {
            logger.error(e.getMessage(), e);
            return Response.build().fail(e);
        } else {
            return Response.build().fail(e.getMessage());
        }
    }

//    @InitBinder
//    protected void InitBinder(WebDataBinder dataBinder) {
//        dataBinder.registerCustomEditor(Date.class, new DateEditor(this, "yyyy-MM-dd hh:mm:ss"));
//    }

    /**
     * 失败
     *
     * @param e        异常对象
     * @param response 响应对象
     */
    protected void fail(Exception e, Response response) {
        fail(e, response, null);
    }

    /**
     * 失败
     *
     * @param e        异常对象
     * @param response 响应对象
     * @param mv       页面
     */
    protected void fail(Exception e, Response response, ModelAndView mv) {
        if (e != null && !(e instanceof BusinessException)) {
            // 系统错误
            logger.error(e.getMessage(), e);
        }
        if (response != null) {
            response.fail(e.getMessage());
        }
        if (mv != null) {
            mv.addObject("response", response);
            mv.setViewName("error");
        }
    }

    /**
     * 执行
     *
     * @param response 应答
     * @param executor 执行器
     * @param <T>
     * @return 应答
     */
    protected <T extends Response> T execute(T response, Executor executor) {
        try {
            executor.execute();
            if (response != null) {
                response.success();
            }
        } catch (Exception e) {
            // 异常错误信息
            fail(e, response);
        }
        return response;
    }


    /**
     * 执行
     *
     * @param response 应答
     * @param view     视图
     * @param executor 执行器
     * @param <T>
     * @return 应答
     */
    protected <T extends Response> ModelAndView execute(T response, ModelAndView view, Executor executor) {
        try {
            executor.execute();
            if (response != null) {
                response.success();
            }
        } catch (Exception e) {
            // 异常错误信息
            fail(e, response, view);
        }
        return view;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setService(S service) {
        this.service = service;
    }

    /**
     * 执行器
     */
    public interface Executor {
        /**
         * 执行
         *
         * @throws Exception
         */
        void execute() throws Exception;
    }

    /**
     * 键值对转换器
     *
     * @param <M>
     */
    public interface DictionaryConverter<M> {

        /**
         * 转换数据为键值对
         *
         * @param model 对象
         * @return 键值对
         */
        Dictionary convert(M model);

    }

}
