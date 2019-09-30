<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<@wrap.page>
    <div>Ссылки на RSS ресурсы</div>

        <<#list rssdatalist as item>

            <div class="card" style="width: 40rem;">
                <img class="card-img-top" src="${item.image}" alt="Card image cap">
                <div class="card-body">
                    <h5 class="card-title">${item.title}</h5>
                    <p class="card-text">${item.title}</p>
                </div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item">${item.dateTime}</li>
                </ul>
                <div class="card-body">
                    <a href="${item.url}" class="card-link">More info</a>
                </div>
            </div>

        <#else>

        </#list>
    <@l.logout/>

</@wrap.page>