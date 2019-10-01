<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<#import "parts/pager.ftl" as p>
<@wrap.page>
    <div>Ссылки на RSS ресурсы</div>

    <@p.pager url rssdatalist/>
    <<#list rssdatalist.content as item>

    <div class="row no-gutters bg-light position-relative">
        <div class="col-md-6 mb-md-0 p-md-4">
            <img src="${item.image}" class="w-100" alt="...">
        </div>
        <div class="col-md-6 position-static p-4 pl-md-0">
            <h5 class="mt-0">${item.title}</h5>
            <p>${item.dateTime}</p>
            <a href="${item.url}" class="stretched-link">Подробнее</a>
        </div>
    </div>

<#else>

</#list>
    <@p.pager url rssdatalist/>
    <@l.logout/>

</@wrap.page>