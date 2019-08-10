package cn.edu.mju.service.serviceImpl;

import cn.edu.mju.BaseTest;
import cn.edu.mju.dto.ShopExecution;
import cn.edu.mju.entity.Area;
import cn.edu.mju.entity.PersonInfo;
import cn.edu.mju.entity.Shop;
import cn.edu.mju.entity.ShopCategory;
import cn.edu.mju.enums.ShopStateEnum;
import cn.edu.mju.service.ShopService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemHeaders;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.swing.MenuItemCheckIconFactory;

import java.io.*;
import java.sql.SQLOutput;

import static org.junit.Assert.*;

public class ShopServiceImplTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    @Ignore
    public void addShop() {

        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        owner.setUserId(1L);
        area.setAreaId(1L);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试");
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setPriority(1);
        shop.setAdvice("审核中");
        FileItem fileItem = new FileItem() {
            @Override
            public InputStream getInputStream() throws IOException {
                return null;
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public boolean isInMemory() {
                return false;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] get() {
                return new byte[0];
            }

            @Override
            public String getString(String s) throws UnsupportedEncodingException {
                return null;
            }

            @Override
            public String getString() {
                return null;
            }

            @Override
            public void write(File file) throws Exception {

            }

            @Override
            public void delete() {

            }

            @Override
            public String getFieldName() {
                return null;
            }

            @Override
            public void setFieldName(String s) {

            }

            @Override
            public boolean isFormField() {
                return false;
            }

            @Override
            public void setFormField(boolean b) {

            }

            @Override
            public OutputStream getOutputStream() throws IOException {
                return null;
            }

            @Override
            public FileItemHeaders getHeaders() {
                return null;
            }

            @Override
            public void setHeaders(FileItemHeaders fileItemHeaders) {

            }
        };
        CommonsMultipartFile shopImg = new CommonsMultipartFile(fileItem);
        ShopExecution shopExecution = shopService.addShop(shop,shopImg);

        System.out.println(shopExecution.getState());

    }

    @Test
    public void modifyShop() {

        Shop shop = shopService.getByShopId(1L);
        shop.setShopName("可爱汉堡");
        shop.setAdvice("添加详细信息");
        ShopExecution shopExecution = shopService.modifyShop(shop,null);
        System.out.println(shopExecution.getShop().getShopName());
    }
}