<template>
  <div>
    <div>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="购物订单" name="first">
          <BuyOrders />
        </el-tab-pane>
        <el-tab-pane label="我的订单" name="second">
          <MyOrders />
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import BuyOrders from "@/views/user/BuyOrders";
import MyOrders from "@/views/user/MyOrders";
export default {
  components: { BuyOrders, MyOrders },
  name: "Orders",
  data() {
    return {
      activeName: "first"
    };
  },
  created() {},
  methods: {
    handleClick(index, tab) {}
  }
};
</script>
<style scoped lang="scss"></style>
