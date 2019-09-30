<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<@wrap.page>
    Login page
    <@l.login "/login" />
    <a href="/registration">Add new user</a>
</@wrap.page>