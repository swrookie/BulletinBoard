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
            <table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
                <thead>
                    <tr>
                        <th colspan="2" style="background-color: #eeeeee; text-align: center;">
                            글 조회
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><input type="text" class="form-control" placeholder="${boardDto.title}" name="title"
                                maxlength="50" / readonly></td>
                    </tr>
                    <tr>
                        <td><input type="text" class="form-control" placeholder="${boardDto.author}" name="author"
                                maxlength="50" / readonly></td>
                    </tr>
                    <tr>
                        <td><textarea class="form-control" placeholder="${boardDto.content}" name="content"
                                maxlength="2048" style="height: 350px;" readonly></textarea></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="container-login100-form-btn m-t-17">
            <button class="btn btn-primary" type="button"
                onclick="location.href='${pageContext.request.contextPath}/go_home_post_update/${boardDto.boardNo}'">
                글 수정
            </button>
            <button class="btn btn-primary pull-right" type="button"
            onclick="location.href='${pageContext.request.contextPath}/delete_post/${boardDto.boardNo}'">
            글 삭제
        </button>
    </div>
</body>

</html>