let board = {  
    init: function() 
    {        
        $("#btn-createPost").on("click", () => {
            this.createBoardDtoAndFile();
        });
        $("#btn-updatePost").on("click", () => {
            this.updateBoardDto();
        }); 
        $("#btn-deletePost").on("click", () => {
            this.deleteBoardDto();
        });
        $("#btn-createComment").on("click", () => {
            this.createCommentDto();
        });
    },   
  
    createBoardDtoAndFile: function() 
    {       
        let boardDto = 
        {
            title: $("#title").val(),
            content: $("#content").val(), 
        };     
 
        var formData = new FormData(document.getElementById("writeForm"));    
        formData.append("boardDto", new Blob([JSON.stringify(boardDto)], {type : "application/json"}));
   
        $.ajax({ 
            type: "POST",
            enctype: "multipart/form-data",
            url: "/post/write",
            data: formData,
            contentType: false,
            processData: false,
            dataType: "json",
        }).done(function(resp) {
            alert("Post Successful!");
            location.href = "/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        }); 
    },
 
    updateBoardDto: function() 
    {
        let boardNo = $("#boardNo").val();

        let boardDto = 
        {
            boardNo: boardNo,
            title: $("#title").val(),
            content: $("#content").val(),
        }; 

        var formData = new FormData(document.getElementById("writeForm"));    
        formData.append("boardDto", new Blob([JSON.stringify(boardDto)], {type : "application/json"}));
 
        $.ajax({
            type: "PUT",
            enctype: "multipart/form-data",
            url: "/post/" + boardNo + "/update",
            data: formData,
            contentType: false,
            processData: false,
            dataType: "json"
        }).done(function(resp) {
            alert("Post Update Successful!");
            location.href="/post/" + boardNo;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },  
   
    deleteBoardDto: function() 
    {   
        let boardNo = $("#boardNo").val();

        $.ajax({
            type:"DELETE",
            url:"/post/" + boardNo,
            dataType:"json"
        }).done(function(resp) {
            alert("Post Delete Successful!");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },

    createCommentDto: function()
    {
        let boardNo = $("#boardNo").val();

        let data = 
        {
            boardNo: boardNo,
            content: $("#content").val(),
        };

        let url = "/post/" + boardNo + "/save";
        console.log(url);

        $.ajax({
            type: "POST",
            url: "/post/" + boardNo + "/save",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            alert("Comment Create Successful!");
            location.href="/post/" + boardNo;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        })
    },
   
    createCommentReplyDto: function (commentNo) 
    {
        let boardNo = $("#boardNo").val();
        let parent = $("#parent" + commentNo).val();
        let data = 
        {
            boardNo: boardNo,
            content: $("#contentReply" + commentNo).val(),
        };
  
        $.ajax({ 
            type: "POST",
            url: "/post/" + boardNo + "/save/" + parent, 
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            alert("Comment Reply Create Successful!");
            location.href="/post/" + boardNo;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },  
   
    updateCommentReplyDto: function(commentNo) 
    { 
        let boardNo = $("#boardNo").val();

        let data = {
            boardNo: boardNo,
            commentNo: commentNo,
            content: $("#contentUpdate" + commentNo).val(),
            depth: $("#depth" + commentNo).val(),
        };  

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

    deleteCommentDto: function(boardNo, commentNo) 
    {           
        $.ajax({
            type:"DELETE",
            url: "/post/" + boardNo + "/comment/" + commentNo,
            dataType:"json"
        }).done(function(resp) {
            alert("Comment Delete Successful!");
            location.href="/post/" + boardNo;
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
}

board.init();