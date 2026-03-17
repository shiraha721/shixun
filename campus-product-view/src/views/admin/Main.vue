<template>
  <div class="dashboard">
    <div class="left">
      <div class="static-count">
        <div v-for="(staticCount, index) in staticCountList" :key="index">
          <el-statistic
            group-separator=","
            :precision="0"
            :value="staticCount.count"
            :title="staticCount.name"
          ></el-statistic>
        </div>
      </div>
      <div style="width: 800px;">
        <LineChart
          @on-selected="onSelected"
          tag="商品上架情况"
          :values="values"
          :date="dates"
          height="600px"
        />
      </div>
    </div>
    <div class="right">
      <h2>最新上架商品</h2>
      <el-col :span="24" v-for="(product, index) in productList" :key="index">
        <div class="item-product">
          <div class="cover">
            <img :src="coverListParse(product)" alt="" srcset="" />
          </div>
          <div
            style="display: flex;justify-content: left;gap: 4px;align-items: center;"
          >
            <span class="bargain-hover">{{
              product.isBargain ? "支持砍价" : "不支持砍价"
            }}</span>
            <span class="title" @click="route(product)">
              {{ product.name }}
            </span>
          </div>
          <div style="padding-block: 15px;">
            <span class="decimel-symbol">¥</span>
            <span class="price">{{ product.price }}</span>
            <span class="love">{{ product.likeNumber }}人想要</span>
          </div>
        </div>
      </el-col>
    </div>
  </div>
</template>
<script>
import LineChart from "@/components/LineChart";
export default {
  components: { LineChart },
  data() {
    return {
      staticCountList: [],
      productList: [],
      values: [], // 商品上架的数量趋势情况
      dates: [], // 商品上架的数量趋势情况对应的时间日期
      days: 365 // 数据较少，直接查询一整年的数据
    };
  },
  created() {
    this.fetchStaticCount();
    this.fetchProductList();
    this.onSelected(this.days);
  },
  methods: {
    route(product) {
      // 跳转商品详情
      this.$router.push("/product-detail1?productId=" + product.id);
    },
    /**
     * 商品封面图处理
     * 从字符串转成可用数组
     * @param {*} product 待处理商品信息
     */
    coverListParse(product) {
      if (product.coverList === null) {
        return;
      }
      const newCoverList = product.coverList.split(",");
      return newCoverList[0];
    },
    /**
     * 商品商家情况日期选择回调事件
     * @param {*} day 日期
     */
    onSelected(day) {
      this.$axios
        .get(`/dashboard/productShelvesInfo/${day}`)
        .then(res => {
          const { data } = res;
          if (data.code === 200) {
            //[{name: '12-10',count:10},{name: '12-11',count:20}]
            // [10,20,....]
            this.values = data.data.map(entity => entity.count);
            this.dates = data.data.map(entity => entity.name);
          }
        })
        .catch(error => {
          console.log("商品商家情况异常:", error);
        });
    },
    fetchStaticCount() {
      this.$axios
        .get("/dashboard/staticCount")
        .then(res => {
          const { data } = res;
          if (data.code === 200) {
            this.staticCountList = data.data;
          }
        })
        .catch(error => {
          console.log("基础数据查询异常:", error);
        });
    },
    fetchProductList() {
      const productQueryDto = {
        size: 3,
        current: 1
      };
      this.$axios
        .post("/product/query", productQueryDto)
        .then(res => {
          const { data } = res;
          if (data.code === 200) {
            this.productList = data.data;
          }
        })
        .catch(error => {
          console.log("查询商品信息异常:", error);
        });
    }
  }
};
</script>
<style scoped lang="scss">
.dashboard {
  display: flex;
  justify-content: left;
  gap: 10px;

  .left {
    padding-block: 22px;
    padding-inline: 30px;
    //background-color: rgb(249, 249, 249);

    .static-count {
      display: flex;
      justify-content: space-evenly;
      gap: 80px;
      margin-bottom: 40px;
    }
  }

  .right {
    box-sizing: border-box;
    width: 500px;

    .item-product {
      padding: 10px 10px 16px 10px;
      box-sizing: border-box;
      border-radius: 15px;
      transition: all 0.5s;
      cursor: pointer;

      .cover {
        img {
          width: 100%;
          height: 120px;
          border-radius: 10px;
        }
      }

      .bargain-hover {
        font-size: 12px;
        font-weight: 800;
        background-color: rgb(255, 230, 15);
        color: rgb(51, 51, 51);
        border-radius: 2px;
        padding: 2px 6px;
      }

      .title {
        font-size: 20px;
        color: #1f1f1f;
      }

      .decimel-symbol {
        font-size: 14px;
        color: #ff4f24;
        font-weight: 800;
      }

      .price {
        font-size: 24px;
        color: #ff4f24;
        font-weight: 800;
        margin-right: 6px;
      }

      .love {
        font-size: 14px;
        color: #999;
      }
    }

    .item-product:hover {
      box-shadow: 1px 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.06);
    }
  }
}
</style>
