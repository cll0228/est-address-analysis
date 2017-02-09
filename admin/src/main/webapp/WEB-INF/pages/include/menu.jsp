<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="nav navbar-nav">

    <li class="menu-dropdown classic-menu-dropdown ">
        <a href="${ctx}/"> 主页
            <span class="arrow"></span>
        </a>
    </li>

    <li class="menu-dropdown classic-menu-dropdown ">
        <a href="javascript:;"> 用户
            <span class="arrow"></span>
        </a>
        <ul class="dropdown-menu pull-left">
            <li class=" "><a class="dropdown-toggle" href="${ctx}/logout.do">注销 </a></li>
        </ul>
    </li>

    <li class="menu-dropdown classic-menu-dropdown ">
        <a href="javascript:;"> 标准地址库
            <span class="arrow"></span>
        </a>
        <ul class="dropdown-menu pull-left">
            <li class=" "><a class="dropdown-toggle" href="${ctx}/newTask.do">新建任务 </a></li>
            <li class=" "><a class="dropdown-toggle" href="${ctx}/dataSourceManage.do">数据源服务器管理 </a></li>
            <li class=" "><a class="dropdown-toggle" href="${ctx}/analysisTask.do">地址解析任务管理</a></li>
            <li class=" "><a class="dropdown-toggle" href="${ctx}/matchTask.do">地址匹配任务管理 </a></li>

        </ul>
    </li>
</ul>
