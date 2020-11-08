<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>Bulletin Board</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- 뷰포트 -->
    <meta name="viewport" content="width=device-width" initial-scale="1">
    <!-- 스타일시트 참조  -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <!-- 애니매이션 담당 JQUERY -->
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>

<body>
    <!-- 게시판 -->

    <div class="container">
        <div class="row">
            <form method="POST" action="${pageContext.request.contextPath}/post_update_page">
                <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                    <thead>
                        <tr>
                            <th colspan="2" style="background-color: #eeeeee; text-align: center;">
                                글 수정
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td><input type="text" class="form-control" value="${boardDto.title}" name="title"
                                    maxlength="50" / readonly></td>
                        </tr>
                        <tr>
                            <td><input type="text" class="form-control" value="${boardDto.author}" name="author"
                                    maxlength="50" / readonly></td>
                        </tr>
                        <tr>
                            <td><textarea class="form-control" name="content" maxlength="2048"
                                    style="height: 350px;">${boardDto.content}</textarea></td>
                        </tr>
                    </tbody>
                </table>
                <input type="text" style="display: none;" value="${boardDto.createDate}" name="createDate" />
                <input type="text" style="display: none;" value="${boardDto.boardNo}" name="boardNo" />
                <input type="submit" class="btn btn-primary pull-right" value="글 수정" />
            </form>
        </div>
    </div>
    </div>
</body>

</html>