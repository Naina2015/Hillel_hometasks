const Page = require('./page');
const Chatpage = Object.create(Page,{
directMessageAtoB: { get: ()=> browserA.element("//span[contains(@class,'p-channel_sidebar')][contains(text(),'echornobai')]")},
directMessageBtoA: { get: ()=> browserB.element("//span[contains(@class,'p-channel_sidebar')][contains(text(),'elena')]")},
messageInputA: { get: ()=> browserA.element('div[aria-label*="echornobai"]')},
messageInputB: { get: ()=> browserB.element('div[aria-label*="elena"]')},
lastMessageInB: {get: ()=>browserB.element('//div[@class="day_container"][last()]//ts-message[last()]//span[@class="message_body"][contains(text(),"Helen")]')},

enterChat: {
    value: function(){
        this.directMessageAtoB.waitForVisible();
        this.directMessageBtoA.waitForVisible();
        this.directMessageAtoB.click();
        this.directMessageBtoA.click();
    }
},
sendMessageFromAToB:{
    value: function(){
        this.messageInputA.setValue('Hello!!').keys('Enter');
        this.messageInputA.setValue('My name is Helen').keys('Enter');
    }
}
});
module.exports = Chatpage;