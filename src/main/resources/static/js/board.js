let index = {
    init: function() 
    {
        $("#btn-create").on("click", () => {
            this.createBoardDtoAndFile();
        });
        $("#btn-update").on("click", () => {
            this.updatePost();
        });
        $("#btn-delete").on("click", () => {
            this.deletePost();
        });
    }, 
 
    createBoardDtoAndFile: function() 
    {       
        let files = $("#customFile")[0]; 
        let boardDto = 
        {
            title: $("#title").val(),
            content: $("#content").val(), 
        };     
 
        var formData = new FormData(document.getElementById("writeForm"));    
         
        formData.append("boardDto", new Blob([JSON.stringify(boardDto)], {type : "application/json"}));
               
        // for (var value of formData.values()) {
        //     console.log(value);   
        // }
   
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

    updatePost: function() 
    {
        let boardNo = $("#boardNo").val();

        let data = {
            boardNo: boardNo,
            title: $("#title").val(),
            content: $("#content").val(),
        };

        $.ajax({
            type: "PUT",
            url: "/post/" + boardNo + "/update",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp) {
            alert("Update Successful!");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },

    deletePost: function() 
    {
        let boardNo = $("#boardNo").val();

        $.ajax({
            type:"DELETE",
            url:"/post/" + boardNo,
            dataType:"json"
        }).done(function(resp) {
            alert("Delete Successful!");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    }
}

index.init();