// package com.zzy.zzyapiinterface.Utils;
//
// import com.unfbx.chatgpt.OpenAiClient;
// import com.unfbx.chatgpt.entity.chat.ChatCompletion;
// import com.unfbx.chatgpt.entity.chat.ChatCompletionResponse;
// import com.unfbx.chatgpt.entity.chat.Message;
// import com.unfbx.chatgpt.function.KeyRandomStrategy;
// import com.unfbx.chatgpt.interceptor.OpenAILogger;
// import com.unfbx.chatgpt.interceptor.OpenAiResponseInterceptor;
// import okhttp3.OkHttpClient;
// import okhttp3.logging.HttpLoggingInterceptor;
//
// import java.net.InetSocketAddress;
// import java.net.Proxy;
// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;
// import java.util.concurrent.TimeUnit;
//
//
//
// public class ChatGptService {
//     private final OpenAiClient openAiClient = getOpenAiClient();
//
//     public String doChat(String userMessage) {
//         //聊天模型：gpt-3.5
//         Message message = Message.builder().role(Message.Role.USER).content(userMessage).build();
//         int len = message.getContent().length();
//         int contentLengthMax = (len + 6300);
//
//         List<Message> lists = new ArrayList<>();
//         lists.add(message);
//         ChatCompletion chatCompletion = ChatCompletion
//                 .builder()
//                 .messages(lists)
//                 .model(ChatCompletion.Model.GPT_3_5_TURBO.getName())
//                 .build();
//         ChatCompletionResponse chatCompletionResponse = null;
//         try {
//             chatCompletionResponse = openAiClient.chatCompletion(chatCompletion);
//         } catch (Exception e) {
//             throw new RuntimeException(e);
//         }
//         StringBuilder builder = new StringBuilder();
//         chatCompletionResponse.getChoices().forEach(e -> {
//             String result = e.getMessage().getContent();
//             builder.append(result);
//         });
//         return builder.toString();
//     }
//
//
//    //export https_proxy=http://127.0.0.1:33210 http_proxy=http://127.0.0.1:33210 all_proxy=socks5://127.0.0.1:33211
//     private OpenAiClient getOpenAiClient() {
//         //可以为null
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 33211));
//         HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new OpenAILogger());
//         //！！！！千万别再生产或者测试环境打开BODY级别日志！！！！
//         //！！！生产或者测试环境建议设置为这三种级别：NONE,BASIC,HEADERS,！！！
//         httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
//         OkHttpClient okHttpClient = new OkHttpClient
//                 .Builder()
// //                .proxy(proxy)
//                 .addInterceptor(httpLoggingInterceptor)
//                 .addInterceptor(new OpenAiResponseInterceptor())
//                 .connectTimeout(10, TimeUnit.SECONDS)
//                 .writeTimeout(30, TimeUnit.SECONDS)
//                 .readTimeout(30, TimeUnit.SECONDS)
//                 .build();
//         return OpenAiClient.builder()
//                 //支持多key传入，请求时候随机选择
//                 //ChatGpt开发密钥
//                 .apiKey(Arrays.asList(""))
//                 //自定义key的获取策略：默认KeyRandomStrategy
//                 .keyStrategy(new KeyRandomStrategy())
//                 .okHttpClient(okHttpClient)
//                 //自己做了代理就传代理地址，没有可不不传
//                 //测试代理地址https://dgr.life/
//                 .apiHost("https://dgr.life/")
//                 .build();
//     }
//
//
// }