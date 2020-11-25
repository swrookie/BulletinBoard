let index2 = {
    init: function() 
    {
        $("#btn-create_comment").on("click", () => {
            this.createComment();
        });
        $("#btn-update_comment").on("click", () => {
            this.updateComment();
        });
    },  
   
    updateComment: function() 
    {
        let boardNo = $("#boardNo").val();
        let commentNo = $('#commentNo').val();
        let data = {
            boardNo: boardNo,
            commentNo: commentNo,
            content: $("#content").val(),
        }; 
           
         for (var value of data.values()) {
            console.log(value);   
        }
        $.ajax({
            type: "PUT",
            url: "/post/" + boardNo + "/comment/" + commentNo,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            alert("Comment Update Successful!");
            location.href="/post/" + boardNo;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },

    deleteComment: function(boardNo, commentNo) 
    {
        $.ajax({
            type:"DELETE",
            url: "/post/${boardNo}/comment/${commentNo}",
            dataType:"json"
        }).done(function(resp) {
            alert("Comment Delete Successful!");
            location.href="/post/${boardNo}";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

index2.init();