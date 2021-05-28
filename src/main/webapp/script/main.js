function scrollFunction() {
    if (document.body.scrollTop > 50 || document.documentElement.scrollTop > 50) {
        document.getElementById("header").style.fontSize = "30px";
        document.getElementById("authorization_icon").style.maxWidth = "25px";
        document.getElementById("header-main-menu-stripes").style.maxHeight = "25px";
        document.getElementById("header-main-auth-font").style.fontSize = "20px";

        var check0 = document.getElementById("main");
        if (check0 != null) {
            check0.style.marginTop = "74px";
        }

        var check = document.getElementById("main-ticket-buy");
        if (check != null) {
            check.style.marginTop = "74px";
        }
    } else {
        document.getElementById("header").style.fontSize = "50px";
        document.getElementById("authorization_icon").style.maxWidth = "35px";
        document.getElementById("header-main-menu-stripes").style.maxHeight = "40px";
        document.getElementById("header-main-auth-font").style.fontSize = "30px";
        var check0 = document.getElementById("main");
        if (check0 != null) {
            check0.style.marginTop = "99px";
        }

        var check = document.getElementById("main-ticket-buy");
        if (check != null) {
            check.style.marginTop = "99px";
        }


    }
}

function hide_show() {
    var x = document.getElementById("main-menu-list");
    if (x.style.display === "none") {
        //window.scroll(0,0);
        x.style.display = "block";
        document.getElementById("header-main-menu-stripes").style.filter = "invert(16%)";
    } else {
        x.style.display = "none";
        document.getElementById("header-main-menu-stripes").style.filter = "invert(100%)";
    }
}

function scrollToUserInfo() {
    window.scrollTo(0, 100);
}

window.onscroll = function () {
    scrollFunction()
};

function changePhone() {
    var x = document.body;
    if (x.style.backgroundImage === "url(\"https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png\")") {
        x.style.backgroundImage = "url(\"https://wallpaperaccess.com/full/203560.jpg\")";
    } else {
        x.style.backgroundImage = "url(\"https://lh4.googleusercontent.com/-XplyTa1Za-I/VMSgIyAYkHI/AAAAAAAADxM/oL-rD6VP4ts/w1184-h666/Android-Lollipop-wallpapers-Google-Now-Wallpaper-2.png\")";
    }

}
