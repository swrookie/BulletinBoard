<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- <%@ page import="java.io.PrintWriter"%> -->

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- 뷰포트 -->
    <meta name="viewport" content="width=device-width" initial-scale="1">
    <!-- 스타일시트 참조  -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <!-- 애니매이션 담당 JQUERY -->
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <!-- 부트스트랩 JS  -->
    <!-- <script src="js/bootstrap.js"></script> -->
    <title>게시판 작성</title>
</head>

<body>
    <!-- 게시판 -->

    <div class="container">
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/go_home_post_read">
                <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                    <thead>
                        <tr>
                            <th colspan="2" style="background-color: #eeeeee; text-align: center;">게시판
                                글쓰기</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" class="form-control" placeholder="글 제목" name="title"
                                    maxlength="50" /></td>
                        </tr>
                        <tr>
                            <td><input type="text" class="form-control" placeholder="글 작성자" name="author"
                                    maxlength="50" /></td>
                        </tr>
                        <tr>
                            <td><textarea class="form-control" placeholder="글 내용" name="content" maxlength="2048"
                                    style="height: 350px;"></textarea></td>
                        </tr>
                    </tbody>
                </table>
                <input type="submit" class="btn btn-primary pull-right" name="page" value="글쓰기" />
            </form>
        </div>
    </div>
</body>

</html>