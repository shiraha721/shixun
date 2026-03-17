<template>
  <div class="message-container">
    <div class="clear-message">
      <span @click="messageIsRead">
        <i class="el-icon-s-open"></i>
      </span>
    </div>
    <div class="item" v-for="(message, index) in messageList" :key="index">
      <div
        class="bell"
        :style="{
          backgroundColor: message.isRead
            ? 'rgb(246,246,246)'
            : 'rgb(239, 193, 87)'
        }"
      >
        <i
          :style="{
            color: message.isRead ? 'rgb(51,51,51)' : 'rgb(246,246,246)'
          }"
          class="el-icon-message-solid"
          >{{ message.isRead ? "已读" : "未读" }}</i
        >
      </div>
      <div>
        <div class="content">{{ message.content }}</div>
        <div class="time">{{ message.createTime }}</div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "Message",
  data() {
    return {
      messageList: []
    };
  },
  created() {
    this.fetchMessage();
  },
  methods: {
    messageIsRead() {
      this.$axios
        .post(`/message/setRead`)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.$notify({
              duration: 1000,
              title: "消息清理成功",
              message: data.msg,
              type: "success"
            });
            this.fetchMessage();
          }
        })
        .catch(error => {
          console.log("消息已读设置异常：", error);
        });
    },
    fetchMessage() {
      this.$axios
        .post(`/message/queryUser`, {})
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.messageList = data.data;
          }
        })
        .catch(error => {
          console.log("查询消息异常：", error);
        });
    }
  }
};
</script>
<style scoped lang="scss">
.message-container {
  .clear-message {
    margin-block: 10px;
    display: flex;
    justify-content: right;
    span:hover {
      background-color: rgb(241, 241, 241);
    }
    span {
      display: flex;
      justify-content: center;
      align-items: center;
      cursor: pointer;
      width: 30px;
      height: 30px;
      border-radius: 50%;
      background-color: rgb(246, 246, 246);
    }
  }
  .item {
    padding: 20px 10px;
    display: flex;
    justify-content: left;
    gap: 20px;
    align-items: center;
    .content {
      margin-block: 10px;
      font-size: 18px;
    }
    .time {
      margin-block: 2px;
      font-size: 12px;
    }
    .bell:hover {
      border: 2px solid rgb(220, 118, 44);
    }
    .bell {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      display: flex;
      align-items: center;
      justify-content: center;
      border: 2px solid rgb(252, 232, 210);
      cursor: pointer;
      transition: all 0.5s;
      i {
        font-size: 12px;
        color: rgb(246, 246, 246);
      }
    }
  }
}
</style>
