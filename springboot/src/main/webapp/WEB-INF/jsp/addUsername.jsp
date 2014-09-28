<html>
    <body>
        <form action="/verifyWithUsername" method="get">
            Add username: <input type="text" name="username"><br>
            <input type="submit" value="Submit"><br>
            <input type="hidden" name="verificationKey" value="${verificationKey}" />
        </form>
    </body>
</html>