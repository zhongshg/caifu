package wap.wx.util;

import java.util.List;

public class PageUtil<T> {

    private int page;
    private int pageSize = 1;
    private int maxSize;
    private int maxPage;
    private List<T> list;
    private int before;
    private int after;
    private String style;
    private String style2;
    private String oldstyle;
    private String servletName;
    private String sign = "";

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getMaxPage() {
        return maxSize % pageSize > 0 ? (maxSize / pageSize) + 1 : maxSize / pageSize;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getBefore() {
        return this.getPage() - 1 < 1 ? 1 : this.getPage() - 1;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return this.getPage() + 1 > this.getMaxPage() ? this.getMaxPage() : this.getPage() + 1;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public String getStyle() {
        StringBuilder sub = new StringBuilder();
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=1&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"First Page\">« 首页</a>&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + this.getBefore() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Previous Page\">« 上一页</a>&nbsp;");
        if (this.getMaxPage() <= 11) {
            for (int i = 1; i <= this.getMaxPage(); i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() > 5 && this.getPage() + 5 < this.getMaxPage()) {
            for (int i = this.getPage() - 5; i <= this.getPage() + 5; i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() <= 5) {
            for (int i = 1; i <= 11; i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() + 5 >= this.getMaxPage()) {
            for (int i = this.getMaxPage() - 10; i <= this.getMaxPage(); i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        }
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + this.getAfter() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Next Page\">下一页 »</a>&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + this.getMaxPage() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Last Page\">尾页 »</a>&nbsp;");
        return sub.toString();
    }

    public String getStyle2() {
        StringBuilder sub = new StringBuilder();
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=1&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"First Page\">« 首页</a>&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + this.getBefore() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Previous Page\">« 上一页</a>&nbsp;");
        if (this.getMaxPage() <= 11) {
            for (int i = 1; i <= this.getMaxPage(); i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() > 5 && this.getPage() + 5 < this.getMaxPage()) {
            for (int i = this.getPage() - 5; i <= this.getPage() + 5; i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() <= 5) {
            for (int i = 1; i <= 11; i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        } else if (this.getPage() + 5 >= this.getMaxPage()) {
            for (int i = this.getMaxPage() - 10; i <= this.getMaxPage(); i++) {
                sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + i + "&sign=" + this.getSign() + "';document.forms[0].submit();\" class=\"number\" title=\"" + i + "\">" + i + "</a>&nbsp;");
            }
        }
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + this.getAfter() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Next Page\">下一页 »</a>&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "&page=" + this.getMaxPage() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Last Page\">尾页 »</a>&nbsp;");
        return sub.toString();
    }

    public String getOldstyle() {
        StringBuilder sub = new StringBuilder();
        sub.append(this.getPage() + "/" + this.getMaxPage() + "&nbsp;&nbsp;共:" + this.getMaxSize() + "&nbsp;&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + this.getBefore() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Previous Page\">« 上一页</a>&nbsp;");
        sub.append("<a href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=" + this.getAfter() + "&sign=" + this.getSign() + "';document.forms[0].submit();\" title=\"Next Page\">下一页 »</a>&nbsp;");
        sub.append(" 跳转：<input id=\"page\" type=\"number\" size=\"2\" />\n"
                + "                <input type=\"button\" id=\"forwards\" onclick=\"javascript:forwards();\" value=\"GO\" style=\"font-size:12px \" />\n"
                + "                <script>\n"
                + "                    $(\"#forwards\").click(function(){\n"
                + "                        var page=$(\"#page\").val();\n"
                + "                        window.location.href=\"javascript:document.forms[0].action='" + this.getServletName() + "?method=getList&page=\"+page+\"&sign=" + this.getSign() + "';document.forms[0].submit();\";\n"
                + "                    })\n"
                + "                </script>");
        return sub.toString();
    }

    public String getServletName() {
        return servletName;
    }

    public void setServletName(String servletName) {
        this.servletName = servletName;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
