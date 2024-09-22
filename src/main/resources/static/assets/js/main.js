document.addEventListener('DOMContentLoaded', function () {
  // Scroll Up/Down 버튼 기능 구현
  const scrollUpBtn = document.getElementById('home-scroll-up');
  const scrollDownBtn = document.getElementById('home-scroll-down');

  if (scrollUpBtn) {
    scrollUpBtn.addEventListener('click', function () {
      window.scrollTo({
        top: 0,
        behavior: 'smooth',
      });
    });
  }

  if (scrollDownBtn) {
    scrollDownBtn.addEventListener('click', function () {
      window.scrollTo({
        top: document.body.scrollHeight,
        behavior: 'smooth',
      });
    });
  }

  // 뉴스 카드 넘김 기능 구현
  const prevBtn = document.querySelector('.prev-btn');
  const nextBtn = document.querySelector('.next-btn');
  const carousel = document.querySelector('.carousel');
  const cardWidth = document.querySelector('.news-card').offsetWidth; // 카드 한 개의 너비 계산
  let scrollAmount = 0;

  nextBtn.addEventListener('click', () => {
    const maxScrollLeft = carousel.scrollWidth - carousel.clientWidth;
    if (scrollAmount < maxScrollLeft) {
      scrollAmount += cardWidth; // 카드 한 개 너비만큼 이동
      carousel.scrollTo({
        left: scrollAmount,
        behavior: 'smooth',
      });
    }
  });

  prevBtn.addEventListener('click', () => {
    if (scrollAmount > 0) {
      scrollAmount -= cardWidth; // 카드 한 개 너비만큼 이동
      carousel.scrollTo({
        left: scrollAmount,
        behavior: 'smooth',
      });
    }
  });
});


//토너먼트페이지

document.addEventListener('DOMContentLoaded', function () {
  const tabLinks = document.querySelectorAll('.tab-link');
  const tabPanels = document.querySelectorAll('.tab-panel');

  tabLinks.forEach(link => {
    link.addEventListener('click', function (event) {
      event.preventDefault();

      // 모든 탭을 비활성화
      tabLinks.forEach(link => link.classList.remove('active'));
      tabPanels.forEach(panel => panel.classList.remove('active'));

      // 클릭된 탭을 활성화
      this.classList.add('active');
      const targetPanel = document.getElementById(this.dataset.tab);
      targetPanel.classList.add('active');
    });
  });
});


//토너먼트페이지 2-2 gallery 카드 넘김 기능 구현
window.addEventListener('load', function () {
  const prevB = document.querySelector('.prevB');
  const nextB = document.querySelector('.nextB');
  const caro = document.querySelector('.caro');
  const cardWidth = document.querySelector('.gallery-card').offsetWidth; // 카드 한 개의 너비 계산
  console.log(cardWidth);

  
    let scrollAmount = 0;

    nextB.addEventListener('click', () => {
      const maxScrollLeft = caro.scrollWidth - caro.clientWidth;
      if (scrollAmount < maxScrollLeft) {
        scrollAmount += cardWidth; // 카드 한 개 너비만큼 이동
        caro.scrollTo({
          left: scrollAmount,
          behavior: 'smooth',
        });
      }
    });

    prevB.addEventListener('click', () => {
      if (scrollAmount > 0) {
        scrollAmount -= cardWidth; // 카드 한 개 너비만큼 이동
        caro.scrollTo({
          left: scrollAmount,
          behavior: 'smooth',
        });
      }
    });

});


document.addEventListener('DOMContentLoaded', function() {
  const openLoginPopupBtn = document.querySelector('.btn-signin');
  const openSubscribeImage = document.querySelector('.footer-image-container img');
  const loginModal = document.getElementById('login-modal');
  const closeLoginBtn = document.querySelector('.login-close-btn');

  // 페이지 로드 시 모달이 기본적으로 숨겨진 상태로 시작
  loginModal.style.display = 'none'; // 혹시 보이는 경우 강제로 숨김

  // 팝업 열기
  if (openLoginPopupBtn) {
    openLoginPopupBtn.addEventListener('click', function(event) {
      event.preventDefault();
      loginModal.style.display = 'flex'; // 모달을 표시
    });
  }

  // 팝업 열기 (이미지 클릭 시)
  if (openSubscribeImage) {
    openSubscribeImage.addEventListener('click', function(event) {
      event.preventDefault(); // 기본 이미지 클릭 동작 방지 (필요 시)
      loginModal.style.display = 'flex'; // 모달을 표시
    });
  }

  // 팝업 닫기
  if (closeLoginBtn) {
    closeLoginBtn.addEventListener('click', function() {
      loginModal.style.display = 'none'; // 모달을 숨김
    });
  }

  // 배경 클릭 시 팝업 닫기
  loginModal.addEventListener('click', function(event) {
    if (event.target === loginModal) {
      loginModal.style.display = 'none'; // 모달 닫기
    }
  });
});

document.addEventListener('DOMContentLoaded', function () {
  const modalContent = document.getElementById('modal-content-innerbox');
  const modal = document.getElementById('login-modal');
  
  // Sign Up 모드 내용 저장
  const signUpContent = modalContent.innerHTML;

  // Sign In 모드로 변경할 내용
  const signInContent = `
    <h1>COME PLAY PICKLEBALL WITH US</h1>
    <h2>PICKLEBALL SEOUL OPEN</h2>
    <h3>가입하여 다음 혜택을 받아보세요!</h3>
    <ul>
      <li>대회, 리그 사전 접근</li>
      <li>다양한 특별할인 및 프로모션 제공</li>
      <li>최신 피클볼 뉴스 및 콘텐츠</li>
    </ul>
    <div class="login-email-input">
      <input type="email" placeholder="Enter Your E-mail">
      <button class="sign-in-btn">
        <img src="../assets/images/kakao-icon.png" alt="User icon" class="chat-icon">
        <span class="divider"></span>
        <h4>SIGN IN</h4>
      </button>
    </div>
  `;

  // Sign Up -> Sign In 전환
  const handleSignUpClick = function () {
    modalContent.innerHTML = signInContent;
    modal.classList.add('sign-in-active'); // Sign In 스타일 활성화
    // Sign In 모드에서 다시 Sign Up 모드로 돌아가는 이벤트 리스너를 추가
    const signInBtn = document.querySelector('.sign-in-btn');
    signInBtn.addEventListener('click', handleSignInClick);
  };

  // Sign In -> Sign Up 전환
  const handleSignInClick = function () {
    modalContent.innerHTML = signUpContent;
    modal.classList.remove('sign-in-active'); // Sign In 스타일 제거
    // Sign Up 모드에서 다시 Sign In 모드로 돌아가는 이벤트 리스너를 추가
    const signUpBtn = document.querySelector('.sign-up-btn');
    signUpBtn.addEventListener('click', handleSignUpClick);
  };

  // 처음 Sign Up -> Sign In 전환 이벤트 리스너 설정
  const signUpBtn = document.querySelector('.sign-up-btn');
  signUpBtn.addEventListener('click', handleSignUpClick);

});


document.addEventListener('DOMContentLoaded', function() {
  const menuToggle = document.querySelector('.menu-toggle');
  const navContainer = document.querySelector('.nav-container');

  menuToggle.addEventListener('click', function() {
    this.classList.toggle('active');
    navContainer.classList.toggle('active');
  });
});

document.addEventListener("DOMContentLoaded", function () {
  const tournamentListContainer = document.querySelector('.tournament-list-container');
  const toggleUpButton = document.getElementById("toggleUpButton");
  const toggleDownButton = document.getElementById("toggleDownButton");

  // 스크롤 다운 버튼 클릭
  toggleDownButton.addEventListener("click", function () {
    tournamentListContainer.scrollBy({
      top: 150, // Amount to scroll down
      behavior: "smooth"
    });
  });

  // 스크롤 업 버튼 클릭
  toggleUpButton.addEventListener("click", function () {
    tournamentListContainer.scrollBy({
      top: -150, // Amount to scroll up
      behavior: "smooth"
    });
  });

  // 스크롤 이벤트
  tournamentListContainer.addEventListener('scroll', function () {
    const scrollTop = tournamentListContainer.scrollTop;
    const scrollHeight = tournamentListContainer.scrollHeight;
    const clientHeight = tournamentListContainer.clientHeight;

    // 스크롤이 끝에 도달했을 때 Down 버튼 숨기기
    if (Math.ceil(scrollTop + clientHeight) >= scrollHeight) {
      toggleDownButton.style.display = "none";
    } else {
      toggleDownButton.style.display = "block";
    }

    // 스크롤이 상단을 벗어났을 때 Up 버튼 보이기
    if (scrollTop > 0) {
      toggleUpButton.style.display = "block";
    } else {
      toggleUpButton.style.display = "none";
    }
  });
});
