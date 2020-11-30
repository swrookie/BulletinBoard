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
        let title = $("#title").val();
        let content = $("#content").val();
 
        if (title == "")
        {
            alert("Please enter title");
            document.writeForm.title.focus();
            return;
        }

        if (content == "")
        {
            alert("Please enter content");
            document.writeForm.content.focus();
            return;
        }

        let boardDto = 
        {
            title: title,
            content: content, 
        };     
        
        var formData = new FormData(document.getElementById("writeForm"));    
        formData.append("boardDto", new Blob([JSON.stringify(boardDto)], {type : "application/json"}));
		
		for (var value of formData.values())
		{
			console.log("Data to be sent from AJAX to BoardController: " + value);
		}
       
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
        let title = $("#title").val();
        let content = $("#content").val();

        if (title == "")
        {
            alert("Please enter title");
            document.writeForm.title.focus();
            return;
        }

        if (content == "")
        {
            alert("Please enter content");
            document.writeForm.content.focus();
            return;
        }

        let boardDto = 
        {
            boardNo: boardNo,
            title: title,
            content: content,
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
 
    deleteFileDto: function(boardNo, fileNo)
    {  
        let url = "/post/" + boardNo + "/update/" + fileNo;
        console.log(url);

        $.ajax({
            type: "GET",
            url: "/post/" + boardNo + "/update/" + fileNo,
            dataType:"json"
        }).done(function(resp) {
            alert("File Delete Successful!");
            location.href="/post/" + boardNo + "/update";
        }).fail(function(error) {
            alert(JSON.stringify(error));
            return;
        });
    },

    createCommentDto: function()
    {
        let boardNo = $("#boardNo").val();
        let content = $("#content").val();

        if (content == "")
        {
            alert("Please enter content");
            document.writeForm.content.focus();
            return;
        }

        let data = 
        {
            boardNo: boardNo,
            content: content,
        };

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
        let content = $("#contentReply" + commentNo).val();

        if (content == "")
        {
            alert("Please enter content");
            document.writeForm.content.focus();
            return;
        }

        let data = 
        {
            boardNo: boardNo,
            content: content,
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
        let content = $("#contentUpdate" + commentNo).val();
        let depth = $("#depth" + commentNo).val();

        if (content == "")
        {
            alert("Please enter content");
            document.writeForm.content.focus();
            return;
        }

        let data = 
        {
            boardNo: boardNo,
            commentNo: commentNo,
            content: content,
            depth: depth,
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