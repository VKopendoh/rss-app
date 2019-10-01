<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<@wrap.page>
   <h3>Авторизация</h3>
    <@l.login "/login" />
    <a href="/registration">Зарегистрироваться</a>
</@wrap.page>