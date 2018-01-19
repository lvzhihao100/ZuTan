package com.gamerole.rongtalk.ui;

import android.databinding.ViewDataBinding;

import com.eqdd.common.base.CommonFullTitleActivity;
import com.gamerole.rongtalk.R;
import com.gamerole.rongtalk.databinding.RongTalkConversationBinding;

import java.util.Iterator;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.TypingMessage.TypingStatus;
import io.rong.imlib.model.Conversation;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

/**
 * @author吕志豪 .
 * @date 18-1-13  下午5:42.
 * Github :https://github.com/lvzhihao100
 * E-Mail：1030753080@qq.com
 * 简书 :http://www.jianshu.com/u/6e525b929aac
 */
public class ConversationActivity extends CommonFullTitleActivity {

    private String title;
    private String mTargetId;
    private Conversation.ConversationType mConversationType;

    @Override
    protected void initBinding(ViewDataBinding inflate) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.rong_talk_conversation;
    }

    @Override
    public void initData() {

    }

    @Override
    public void setView() {
        title = getIntent().getData().getQueryParameter("title");
        mTargetId = getIntent().getData().getQueryParameter("targetId");
        mConversationType = Conversation.ConversationType.valueOf(getIntent().getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        initTopTitleBar(title);
        RongIMClient.setTypingStatusListener((type, targetId, typingStatusSet) -> {
            //当输入状态的会话类型和targetID与当前会话一致时，才需要显示
            if (type.equals(mConversationType) && targetId.equals(mTargetId)) {
                //count表示当前会话中正在输入的用户数量，目前只支持单聊，所以判断大于0就可以给予显示了
                int count = typingStatusSet.size();
                if (count > 0) {
                    Iterator iterator = typingStatusSet.iterator();
                    TypingStatus status = (TypingStatus) iterator.next();
                    String objectName = status.getTypingContentType();

                    MessageTag textTag = TextMessage.class.getAnnotation(MessageTag.class);
                    MessageTag voiceTag = VoiceMessage.class.getAnnotation(MessageTag.class);
                    //匹配对方正在输入的是文本消息还是语音消息
                    if (objectName.equals(textTag.value())) {
                        //显示“对方正在输入”
                        AndroidSchedulers.mainThread().scheduleDirect(() -> {
                            initTopTitleBar("对方正在输入");
                        });

                    } else if (objectName.equals(voiceTag.value())) {
                        //显示"对方正在讲话"
                        AndroidSchedulers.mainThread().scheduleDirect(() -> {
                            initTopTitleBar("对方正在讲话");
                        });
                    }
                } else {
                    //当前会话没有用户正在输入，标题栏仍显示原来标题
                    AndroidSchedulers.mainThread().scheduleDirect(() -> {
                        initTopTitleBar(title);
                    });
                }
            }
        });

    }
}