<template>
  <div class="ai-chat">
    <div class="chat-container">
      <div class="messages" ref="messagesRef">
        <div
          v-for="(msg,index) in messages"
          :key="index"
          :class="['message', msg.role]"
        >
          <div class="avatar">
            <img v-if="msg.role==='assistant'" src="/logo.png" />
            <img v-else src="/logo.jpg" />
          </div>
          <div class="bubble">{{ msg.content }}</div>
        </div>
      </div>

      <div class="input-area">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请输入内容..."
          v-model="input"
          @keyup.enter.native="send"
        />
        <el-button type="primary" @click="send">发送</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'AiChat',
  data() {
    return {
      input: '',
      messages: []
    }
  },
  methods: {
    async send() {
      const text = this.input.trim()
      if (!text) return

      this.messages.push({ role: 'user', content: text })
      this.input = ''

      const msgList = this.messages.map(m => m.content)

      const res = await request.post('/ai/chat', {
        messages: msgList
      })

      const reply = res.data.data.reply

      this.messages.push({ role: 'assistant', content: reply })

      this.$nextTick(() => {
        const el = this.$refs.messagesRef
        el.scrollTop = el.scrollHeight
      })
    }
  }
}
</script>

<style scoped>
.ai-chat {
  height: 100%;
  display: flex;
  justify-content: center;
}

.chat-container {
  width: 900px;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
}

.message {
  display: flex;
  margin-bottom: 16px;
}

.message.user {
  flex-direction: row-reverse;
}

.avatar img {
  width: 36px;
  height: 36px;
  border-radius: 50%;
}

.bubble {
  max-width: 70%;
  padding: 10px 14px;
  border-radius: 8px;
  margin: 0 10px;
  background: #f5f5f5;
}

.message.user .bubble {
  background: #409eff;
  color: white;
}

.input-area {
  padding: 15px;
  border-top: 1px solid #eee;
  display: flex;
  gap: 10px;
}
</style>
