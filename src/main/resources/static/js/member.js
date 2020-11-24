let index = {
    init: function() 
    {
        $("btn-createMember").on("click", () => {
            this.createMember();
        });
        $("btn-login").on("click", () => {
            this.login();
        });
    }
}

index.init();