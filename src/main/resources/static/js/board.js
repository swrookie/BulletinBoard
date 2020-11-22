let index = {
    init: function() {
        this.$("#btn-create").on("click", () => {
            this.createPost();
        });
        $("btn-update").on("click", () => {
            this.updatePost();
        });
        $("btn-delete").on("click", () => {
            this.deletePost();
        });
    },

    createPost: function() {
        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };
    
        $.ajax({ 
            type: "POST",
            url: "/post",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("Post Successful!");
            location.href = "/";
        }).fail(function(error){
            alert(JSON.stringify(error));
        }); 
    },

    updatePost: function() {
        let boardNo = $("#boardNo").val();

        let data = {
            title: $("#title").val(),
            content: $("#content").val()
        };

        $.ajax({
            type: "PUT",
            url: "/post/" + boardNo,
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json"
        }).done(function(resp){
            alert("Update Successful!"),
            location.href="/"
        }).fail(function(error){
            alert(JSON.stringify(error));
        });
    },

    deletePost: function() {
        var id = $("#boardNo").val();

        $.ajax({
            type:"DELETE",
            url:"/post/" + id,
            dataType:"json"
        }).done(function(resp) {
            alert("Delete Successful!");
            location.href="/";
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
}

index.init();