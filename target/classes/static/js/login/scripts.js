jQuery(document).ready(function () {

    $('.page-container form').submit(function () {
        var username = $(this).find('.username').val();
        var password = $(this).find('.password').val();

        // var username = $('#username').val();
        // var password = $('#password').val();
        if (username == '') {
            $(this).find('.error').fadeOut('fast', function () {
                $(this).css('top', '27px');
            });
            $(this).find('.error').fadeIn('fast', function () {
                $(this).parent().find('.username').focus();
            });
            return false;
        } else if (password == '') {
            $(this).find('.error').fadeOut('fast', function () {
                $(this).css('top', '96px');
            });
            $(this).find('.error').fadeIn('fast', function () {
                $(this).parent().find('.password').focus();
            });
            return false;
        } else {
            // alert(username + "--" + password);

            $.ajax({
                type: "POST",
                // url: "http://localhost:8084/login",
                url: "http://localhost:8084/threadTest",
                dataType: "json",
                data: {
                    name: username,
                    password: password
                },
                success: function (data) {
                    // alert(data.toString());
                    if (0 == data.msgCode) {
                        //窗体重新加载
                    }
                }
            });
            return true;
        }
    });

    $('.page-container form .username, .page-container form .password').keyup(function () {
        $(this).parent().find('.error').fadeOut('fast');
    });

});