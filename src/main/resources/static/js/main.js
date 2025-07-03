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
                    window.location.href = '/view/home'; // 예시: 대시보드 페이지로 이동
                },
                error: function(error) {
                    // 로그인 실패 시 처리
                    alert('로그인 실패: ' + error.responseText);
                }
            });
        });

        // Form submission - 개선된 버전
        var formSubmission = function() {
            // 로그인 폼 제출 처리
            $('#login-form').on('submit', function(e) {
                e.preventDefault(); // 기본 폼 제출 방지

                var formData = $(this).serialize(); // 폼 데이터 직렬화

                // 로딩 상태 표시
                var submitBtn = $(this).find('button[type="submit"]');
                var originalText = submitBtn.text();
                submitBtn.text('로그인 중...').prop('disabled', true);

                $.ajax({
                    type: 'POST',
                    url: $(this).attr('action'),
                    data: formData,
                    dataType: 'json', // JSON 응답 예상
                    success: function(response) {
                        // 응답 데이터의 성공/실패 여부를 확인
                        if (response.success === true || response.status === 'success') {
                            // 로그인 성공 시 처리
                            alert('로그인 성공!');
                            window.location.href = response.redirectUrl || '/view/home';
                        } else {
                            // 서버에서 성공 응답이지만 로그인 실패인 경우
                            alert('로그인 실패: ' + (response.message || '아이디 또는 비밀번호가 올바르지 않습니다.'));
                        }
                    },
                    error: function(xhr, status, error) {
                        // HTTP 오류 상태 코드 처리
                        var errorMessage = '로그인 실패: ';

                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            errorMessage += xhr.responseJSON.message;
                        } else if (xhr.responseText) {
                            try {
                                var errorResponse = JSON.parse(xhr.responseText);
                                errorMessage += errorResponse.message || errorResponse.error || '알 수 없는 오류가 발생했습니다.';
                            } catch (e) {
                                errorMessage += xhr.responseText;
                            }
                        } else {
                            errorMessage += '서버 연결에 실패했습니다.';
                        }

                        alert(errorMessage);
                    },
                    complete: function() {
                        // 로딩 상태 해제
                        submitBtn.text(originalText).prop('disabled', false);
                    }
                });
            });
        };

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
                    window.location.href = '/view/login'; // 예시: 환영 페이지로 이동
                },
                error: function(error) {
                    // 회원가입 실패 시 처리
                    alert('회원가입 실패: ' + error.responseText);
                }
            });
        });
    };
	
	var formSubmission2 = function() {
	            $('#register-item-form').on('submit', function(event) {
	                event.preventDefault();
	                var selectedCategories = $('#categories').val();
	                var formData = {
	                    id: $('#itemId').val(),
	                    name: $('#name').val(),
	                    origin: $('#origin').val(),
	                    price: $('#price').val(),
	                    stockQuantity: $('#stockQuantity').val(),
	                    categories: selectedCategories.map(id => ({ id: id }))
	                };
	                $.ajax({
	                    url: formData.id ? '/items/' + formData.id : '/items/register',
	                    type: formData.id ? 'PUT' : 'POST',
	                    contentType: 'application/json',
	                    data: JSON.stringify(formData),
	                    success: function(response) {
	                        alert('Item ' + (formData.id ? 'updated' : 'registered') + ' successfully!');
	                        window.location.href = '/items';
	                    },
	                    error: function(error) {
	                        console.error('Error:', error);
	                    }
	                });
	            });
	        };


    // On load
    $(function(){
        placeholderFunction();
        contentWayPoint();
        formSubmission();
		formSubmission2();
    });

}());