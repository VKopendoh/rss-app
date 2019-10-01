<#macro login path>

    <form action="${path}" method="post">
        <div class="form-group has-feedback">
            <label for="login" class="control-label col-xs-3">Логин:</label>
            <div class="col-xs-6">
                <div class="input-group">
                    <input type="text" class="form-control" required="required" name="username" pattern="[A-Za-z]*{1,15}"
                    title="Login must starts with latin letters">
                </div>
            </div>
        </div>
        <div class="form-group">
            <label for="exampleInputPassword1">Пароль:</label>
            <input type="password" class="form-control" id="exampleInputPassword1" name="password" placeholder="Password">
        </div>
        <button type="submit" class="btn btn-secondary">Подтвердить</button>
    </form>

</#macro>

<#macro logout>
    <form action="/logout" class="pt-5" method="post">
       <#-- <input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <input type="submit" class="btn btn-secondary" value="Log Out" />
    </form>
</#macro>