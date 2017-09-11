const Page =require('./page');

const LoginPage=Object.create(Page,{
    usernameInputA:{get:()=>browserA.element('input#email')},
    usernameInputB:{get:()=>browserB.element('input#email')},
    passwordInputA:{get:()=>browserA.element('input#password')},
    passwordInputB:{get:()=>browserB.element('input#password')},
    signinForm:{get:()=>$('#signin_form')},
    teamOwnerNameA:{get:()=>browserA.element('span#team_menu_user_name')},
    teamOwnerNameB:{get:()=>browserB.element('span#team_menu_user_name')},
    
    inputLoginCreds:{
        value:function(inputLogin,inputPass,username,pass){
            inputLogin.setValue(username);
            inputPass.setValue(pass);

        }
    
    },
    open: {
        value: function(){
            Page.open.call(this,'signin')
        }
    },
    submitFormAndLogin:{
        value:function(){
            this.signinForm.submitForm();
            expect(this.teamOwnerNameA.isExisting()).to.be.equal(true);
            expect(this.teamOwnerNameB.isExisting()).to.be.equal(true);
        }
}})
module.exports=LoginPage;