<#import "parts/common.ftl" as wrap>
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
                <td><a href="/delete?id=${link.id}"
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

    <form method="post">
        <div class="form-group">
            <input type="text" name="url" class="form-control" aria-describedby="emailHelp"
                   placeholder="Введите url для RSS">
        </div>
        <button type="submit" class="btn btn-secondary">Добавить</button>
    </form>


</@wrap.page>