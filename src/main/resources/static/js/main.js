;(function () {
    'use strict';

    // Placeholder 
    var placeholderFunction = function() {
        $('input, textarea').placeholder({ customClass: 'my-placeholder' });
    }
    
    // Content WayPoint 
    var contentWayPoint = function() {
        var i = 0;
        $('.animate-box').waypoint( function( direction ) {
            if( direction === 'down' && !$(this.element).hasClass('animated-fast') ) {
                i++;
                $(this.element).addClass('item-animate');
                setTimeout(function(){
                    $('body .animate-box.item-animate').each(function(k){
                        var el = $(this);
                        setTimeout( function () {
                            var effect = el.data('animate-effect');
                            if ( effect === 'fadeIn') {
                                el.addClass('fadeIn animated-fast');
                            } else if ( effect === 'fadeInLeft') {
                                el.addClass('fadeInLeft animated-fast');
                            } else if ( effect === 'fadeInRight') {
                                el.addClass('fadeInRight animated-fast');
                            } else {
                                el.addClass('fadeInUp animated-fast');
                            }
                            el.removeClass('item-animate');
                        },  k * 200, 'easeInOutExpo' );
                    });
                }, 100);
            }
        } , { offset: '85%' } );
    };

    // Form submission
    var formSubmission = function() {
        // 로그인 폼 제출 처리
        $('#login-form').on('submit', function(e) {
            e.preventDefault(); // 기본 폼 제출 방지

            var formData = $(this).serialize(); // 폼 데이터 직렬화

            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: formData,
                success: function(response) {
                    // 로그인 성공 시 처리
                    alert('로그인 성공!');
                    window.location.href = '/home'; // 예시: 대시보드 페이지로 이동
                },
                error: function(error) {
                    // 로그인 실패 시 처리
                    alert('로그인 실패: ' + error.responseText);
                }
            });
        });

        // 회원가입 폼 제출 처리
        $('#signup-form').on('submit', function(e) {
            e.preventDefault(); // 기본 폼 제출 방지

            var formData = $(this).serialize(); // 폼 데이터 직렬화

            $.ajax({
                type: 'POST',
                url: $(this).attr('action'),
                data: formData,
                success: function(response) {
                    // 회원가입 성공 시 처리
                    alert('회원가입 성공!');
                    window.location.href = '/login'; // 예시: 환영 페이지로 이동
                },
                error: function(error) {
                    // 회원가입 실패 시 처리
                    alert('회원가입 실패: ' + error.responseText);
                }
            });
        });
    };

    // Items button click event
    var itemsButtonClick = function() {
        var itemsButton = document.getElementById('items-button');
        if (itemsButton) {
            itemsButton.addEventListener('click', function() {
                window.location.href = '/items';
            });
        }
    };

    // On load
    $(function(){
        placeholderFunction();
        contentWayPoint();
        formSubmission();
        itemsButtonClick();
    });

}());