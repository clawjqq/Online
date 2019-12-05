package com.chen.servlet;

import com.alibaba.druid.util.StringUtils;
import com.chen.entry.Goods;
import com.chen.entry.GoodsType;
import com.chen.entry.GoodsVo;
import com.chen.service.GoodsService;
import com.chen.service.GoodsTypeService;
import com.chen.service.impl.GoodsServiceImpl;
import com.chen.service.impl.GoodsTypeServiceImpl;
import com.chen.utils.InfoUtils;
import com.chen.utils.Page;
import com.chen.utils.UUIDUtils;
import com.jspsmart.upload.*;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@WebServlet("/goods/*")
public class GoodsServlet extends BaseServlet {

    GoodsService goodsService = new GoodsServiceImpl();
    GoodsTypeService goodsTypeService = new GoodsTypeServiceImpl();

//展示商品页面******************************************************************************************************
    public void list(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Map<String, String[]> map = request.getParameterMap();
        //GoodsVo里面存放的就是查询条件
        GoodsVo goodsVo = new GoodsVo(); //也可以接收查询条件【3个】 分页数据【2个】

        try {
            BeanUtils.populate(goodsVo, map); //将数据放到goodsVo里面去
        } catch (Exception e) {
            e.printStackTrace();
        }
        //计算一下Begin的值， 第几页(page-1)乘以一页几条(Rows)
        goodsVo.setBegin((goodsVo.getPage()-1)*goodsVo.getRows());

        List<Goods> list = null;
        List<GoodsType> listt = null;

        list = goodsService.getGoodsList(goodsVo);
        listt = goodsTypeService.getGoodsTypeList();

        //循环list集合，获取路径名+文件名
        for (Goods goods : list) {
            goods.setImagePath(InfoUtils.getParameter("imagesUrl") + goods.getImagePath());
        }

        System.out.println(list.get(0).getTypename());
        //request.setAttribute("goodsList", list);
        request.setAttribute("typeList", listt);

        //引入分页
        Page<Goods> page = new Page<Goods>();
        page.setPage(goodsVo.getPage()); //当前第几页
        page.setSize(goodsVo.getRows()); //一页有几条
        page.setTotal(goodsService.getCount(goodsVo)); //总共多少条
        page.setRows(list); //当前页的数据
        request.setAttribute("page", page); //此处必须叫page,包中写死了

        //下面是将查询条件再塞回去，展示到查询框中
        /*request.setAttribute("goodsName", goodsVo.getGoodsName());
        request.setAttribute("date", goodsVo.getDate());
        request.setAttribute("typeId", goodsVo.getTypeId());*/

        request.setAttribute("goodsVo", goodsVo);



  try {
            request.getRequestDispatcher("/goods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//展示新增页面，如果是编辑就让内容出现在input框中，如果是添加就不显示******************************************************
    public void addGoods(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 查询商品类别，放置在页面上  商品类别的下拉框
        List<GoodsType> list = goodsTypeService.getGoodsTypeList();
        //获取 t_goodstype 表中的内容
        request.setAttribute("goodsTypeList", list);

        System.out.println("获取了-" + list.size() + "条数据");
        try {
            request.getRequestDispatcher("/addGoods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//提交form表单 新增或者修改商品 ***************************************************************************************
    public void save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SmartUploadException {
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Goods goods = new Goods();

        //获取文件名，得到后缀，然后通过uuid+后缀名得到一个新的文件名
        //编写jspSmartUpload代码

        SmartUpload smartUpload = new SmartUpload();

        //初始化
        smartUpload.initialize(this.getServletConfig(), request, response);
        try {
            //上传
            smartUpload.upload();
        } catch (SmartUploadException e) {
            e.printStackTrace();
        }

        Files files = smartUpload.getFiles(); //得到表单中的所有文件
        System.out.println(files.getSize()); //注意：可以得到表单中上传文件的大小
        System.out.println(files.getCount()); //获取表单文件个数

        // 判断一下这个文件夹是否存在 在编译后的文件夹
        //String path = this.getServletContext().getRealPath("./") + "upload\\";
        //因为要和newName拼接，newName没加斜杠，所以在配置文件后加上斜杠

        String path = InfoUtils.getParameter("imagesPath");
        System.out.println("获取的图库路径位置是："+ path);

        //这个File和上面的不同包
        java.io.File fileDir = new java.io.File(path);

        //判断文件夹是否没有，如果没有就创建一个
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String newNames = "";
        //循环按获取的一些文件
        for (int i = 0; i < files.getCount(); i++)
        {
            File file = files.getFile(i);

            //如果其中一个file不存在,就不应该再往下走
            if (file.isMissing()) {
                // break    跳出for循环，进入下面的步骤--但是如果有多张图片，会导致其他图片不保存
                // return   会导致如果有一张图片没选，方法就消失了其他属性也不保存了
                // continue 所以使用continue;
                continue;
            }

            System.out.println("要上传的文件名字是: "+ file.getFileName());
            String fileName = file.getFileName();
            String suffix = fileName.substring(fileName.lastIndexOf("."));

            String newName = UUIDUtils.getUUID() + suffix;
            System.out.println("生成的新文件名字是： "+ newName);

            // saveAs 另存为的意思
            // File.SAVEAS_PHYSICAL)放置在系统根目录C盘 物理
            // File.SAVEAS_VIRTUAL 放置在项目编译后的路径下 虚拟
            // File.SAVEAS_AUTO 根据环境自动选择虚拟还是物理，可以解决两张图片的问题。
            file.saveAs(path + newName, File.SAVEAS_AUTO);
            //smartUpload.save(path+newName, File....); 两个保存用一个就行了

//不知道下面这个什么作用？
            if (i == files.getCount() - 1) {
                newNames += newName;
            }else {
                newNames += newName + ",";
            }
        }



        goods.setImagePath(newNames);
        Request req = smartUpload.getRequest();

        //从request对象中获取值，要跟你form表单中的值对应, 与input框的name相同, 只提交input框中的内容
        String id = req.getParameter("id");
        String goodsName = req.getParameter("goodsName");
        String price = req.getParameter("price");
        String typeId = req.getParameter("typeId");
        String comment = req.getParameter("comment");

//不知道下面这个啥作用？
        String name = req.getParameter("name");
        System.out.println("价格、商品名、不知道啥的：" + price + "," + goodsName + "," + name);
        System.out.println("打印出了filName: " + req.getParameter("fileName"));


        //如果ID不为空，将ID转为int值，添加到goods
        if (id != null && !id.equals("")) {
            int tid = Integer.parseInt(id);
            goods.setId(tid);
        }

        goods.setGoodsName(goodsName);
        goods.setPrice(Float.valueOf(price));
        goods.setTypeId(Integer.valueOf(typeId));
        goods.setComment(comment);

        int result = goodsService.saveOrUpdate(goods);

        if (result > 0) {
            System.out.println("添加数据成功");
        }
            response.sendRedirect(request.getContextPath() + "/goods/list");

    }


// 点击编辑 edit 按键，显示修改页面，同时默认显示出选择的商品内容************************************************************
    public void edit(HttpServletRequest request, HttpServletResponse response)
    {
        String goodsId = request.getParameter("goodsId"); //商品的id
        Goods goods = goodsService.getGoodsById(goodsId); //根据商品id查询出来商品传给addGoods
        System.out.println("点击修改获得的商品ID是：" + goods.getId());
        System.out.println("点击修改获得的商品名字是：" + goods.getGoodsName());

        //判断imagePath是否为空，不为空了再在前面加上URL
        if (!StringUtils.isEmpty(goods.getImagePath())) {
            goods.setImagePath(InfoUtils.getParameter("imagesUrl") + goods.getImagePath());
        }

        request.setAttribute("gds", goods);

        //商品类型需要传递给页面，页面下拉框才会显示出来
        List<GoodsType> list = goodsTypeService.getGoodsTypeList();
        request.setAttribute("goodsTypeList", list);

        try {
            request.getRequestDispatcher("/addGoods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 点击删除 delet
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        String goodsId = request.getParameter("goodsID");
        int id = Integer.parseInt(goodsId);
        int result = goodsService.deleteById(id);
        if (result > 0) {
            System.out.println("删除成功");
        }
        try {
            response.sendRedirect(request.getContextPath()+"/goods/list");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 批量删除
    public void batchDelete(HttpServletRequest request, HttpServletResponse response) {
        /*request.getParameterValues(String name)是获得如checkbox类(名字相同，但值有多个)的数据
        可以用于接收数组变量 ，如checkobx类型
        request.getParameter(String name)是获得相应名的数据，如果有重复的名，则返回第一个的值 .
        一般用于接收一般变量 ，如text类型*/

        String[] ids = request.getParameterValues("ids");
        System.out.println("要删除的商品ID有：" + Arrays.toString(ids));

        goodsService.batchDelete(ids);
        try {
            response.sendRedirect(request.getContextPath()+"/goods/list");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }








}
