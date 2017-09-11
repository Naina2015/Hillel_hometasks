const LoginPage=require('../pageobjects/login.page');
const ChatPage=require('../pageobjects/chat.page');

describe('login and enter the chat', function(){
    it('should open login page',function(){
        LoginPage.open();
    })
    it('should be able to login from 2 accounts',function(){
        LoginPage.inputLoginCreds(LoginPage.usernameInputA, LoginPage.passwordInputA,
            'elena.chornobai@gmail.com', 'hillelproject123');

        LoginPage.inputLoginCreds(LoginPage.usernameInputB, LoginPage.passwordInputB,
            'echornobai@intersog.com', 'hillelproject000');  
            browser.sync();
            LoginPage.submitFormAndLogin();

    })

    it('should enter the chat', function(){
        ChatPage.enterChat();
    })

    it('browser A should send the message to browser B', function(){
        ChatPage.sendMessageFromAToB();
    })

    })
