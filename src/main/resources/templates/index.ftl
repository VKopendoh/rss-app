<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<@wrap.page>
    <h3>Войти: </h3>
    <@l.login "/login" />
    <a href="/registration" class="pt-2">Зарегистрироваться</a>
    <a href="/h2-console" class="pt-1"> console</a>
</@wrap.page>