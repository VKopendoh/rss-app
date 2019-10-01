<#import "parts/common.ftl" as wrap>
<#import "parts/logcontrol.ftl" as l>
<@wrap.page>
    <div>Ссылки на RSS ресурсы</div>
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">RSS URL</th>
            <th scope="col">Action</th>
        </thead>
        <tbody>
        <#list rsslinks as link>

            <tr>
                <td>${link.url} </td>
                <td><a href="/delete?id=${link.url}"
                       onclick="if(!(confirm('Are you sure you want to delete this customer?'))) return false">
                        Delete</a>
                </td>
            </tr>
        <#else>
            <tr>
                <th scope="row"> No RSS Links</th>
            </tr>
        </#list>
        </tbody>
    </table>
    <br>

    <form method="post" name="add">
        <div class="form-group">

            <input type="text" name="url" class="form-control" pattern="https?://.+" title="Input url"
                   placeholder="Введите url для RSS">
        </div>
        <#-- <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <button type="submit" class="btn btn-secondary">Добавить</button>
    </form>

    <@l.logout />
</@wrap.page>