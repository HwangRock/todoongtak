package view;

public class UserView {

    public static String signupForm() {
        return """
                <!DOCTYPE html>
                <html lang="ko">
                <head>
                    <meta charset="UTF-8">
                    <title>회원가입</title>
                </head>
                <body>
                    <h1>회원가입</h1>
                    <form method="POST" action="/signup">
                        이름: <input type="text" name="username"><br>
                        아이디: <input type="text" name="userid"><br>
                        비밀번호: <input type="password" name="password"><br>
                        <button type="submit">가입하기</button>
                    </form>
                </body>
                </html>
                """;
    }

    public static String loginForm(){
        return """
            <!DOCTYPE html>
            <html lang="ko">
            <head>
                <meta charset="UTF-8">
                <title>로그인</title>
            </head>
            <body>
                <h1>로그인</h1>
                <form method="POST" action="/login">
                    아이디: <input type="text" name="userid"><br>
                    비밀번호: <input type="password" name="password"><br>
                    <button type="submit">로그인</button>
                </form>
            </body>
            </html>
            """;
    }

    public static String successPage(String name) {
        return "<h1>" + name + "님, 회원가입을 축하합니다!</h1>";
    }

    public static String alreadyExistsPage() {
        return "<h1>이미 존재하는 사용자입니다!</h1>";
    }
}
