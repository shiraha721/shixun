<template>
  <div>
    <div class="nav-category">
      <div class="left">
        <span
          :style="{
            color:
              categorySelectedItem.name === isUseCategory.name
                ? 'rgb(51, 51, 51)'
                : '',
            backgroundColor:
              categorySelectedItem.name === isUseCategory.name
                ? 'rgb(254, 223, 70)'
                : ''
          }"
          @click="categorySelected(isUseCategory)"
          :key="index"
          v-for="(isUseCategory, index) in isUseCategoryList"
        >
          {{ isUseCategory.name }}
        </span>
      </div>
    </div>
    <div class="nav-category">
      <div class="right">
        <span class="bargain">
          <span
            :style="{
              color:
                bargainSelectedItem.name === bargain.name
                  ? 'rgb(51,51,51)'
                  : '',
              backgroundColor:
                bargainSelectedItem.name === bargain.name
                  ? 'rgb(254, 223, 70)'
                  : ''
            }"
            @click="bargainSelected(bargain)"
            v-for="(bargain, index) in bargainStatus"
            :key="index"
            >{{ bargain.name }}</span
          >
        </span>
        <el-date-picker
          style="width: 216px;margin-right: 5px;"
          @change="fetchFreshData"
          size="small"
          v-model="searchTime"
          type="daterange"
          range-separator="至"
          start-placeholder="发布开始"
          end-placeholder="发布结束"
        >
        </el-date-picker>
        <el-select
          style="width: 100px;margin-right: 5px;"
          @change="fetchFreshData"
          size="small"
          v-model="productQueryDto.categoryId"
          placeholder="商品类别"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          >
          </el-option>
        </el-select>
      </div>
    </div>
    <div class="product-list">
      <el-row v-if="productList.length === 0">
        <el-empty description="暂无商品信息"></el-empty>
      </el-row>
      <el-row v-else>
        <el-col
          @click.native="route(product)"
          :span="6"
          v-for="(product, index) in productList"
          :key="index"
        >
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
              <span class="title">
                {{ product.name }}
              </span>
            </div>
            <div style="padding-block: 15px;">
              <span class="decimel-symbol">¥</span>
              <span class="price">{{ product.price }}</span>
              <span class="love">{{ product.likeNumber }}人想要</span>
            </div>
            <div class="info">
              <img :src="product.userAvatar" alt="" srcset="" />
              <span>{{ product.userName }}</span>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
export default {
  name: "Product",
  data() {
    return {
      categoryList: [], // 存储的商品类别数组
      isUseCategoryList: [], // 存储的启用的类别数组
      categorySelectedItem: {},
      productQueryDto: {}, // 商品查询条件类
      productList: [], // 存储后端返回的商品数据列表
      bargainSelectedItem: {},
      searchTime: [],
      bargainStatus: [
        { isBargain: null, name: "全部" },
        { isBargain: true, name: "支持砍价" },
        { isBargain: false, name: "不支持砍价" }
      ]
    };
  },
  created() {
    this.fetchFreshData();
    this.fetchCategoryList();
    // 页面加载时，默认不启用砍价查询条件
    this.bargainSelected(this.bargainStatus[0]);
  },
  methods: {
    route(product) {
      // 跳转商品详情
      this.$router.push("/product-detail?productId=" + product.id);
    },
    coverListParse(product) {
      if (product.coverList === null) {
        return;
      }
      const newCoverList = product.coverList.split(",");
      return newCoverList[0];
    },
    /**
     * 商品砍价选中事件
     * @param {*} bargain
     */
    bargainSelected(bargain) {
      this.bargainSelectedItem = bargain;
      this.productQueryDto.isBargain = bargain.isBargain;
      this.fetchFreshData();
    },
    /**
     * 查询商品数据
     */
    async fetchFreshData() {
      let startTime = null;
      let endTime = null;
      if (this.searchTime != null && this.searchTime.length === 2) {
        const [startDate, endDate] = await Promise.all(
          this.searchTime.map(date => date.toISOString())
        );
        startTime = `${startDate.split("T")[0]}T00:00:00`;
        endTime = `${endDate.split("T")[0]}T23:59:59`;
      }
      // this.productQueryDto.current = this.currentPage;
      // this.productQueryDto.size = this.pageSize;
      this.productQueryDto.startTime = startTime;
      this.productQueryDto.endTime = endTime;
      this.$axios
        .post("/product/query", this.productQueryDto)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.productList = data.data;
          }
        })
        .catch(error => {
          console.log("商品查询异常：", error);
        });
    },
    /**
     * 商品类别选中事件
     * @param {*} category
     */
    categorySelected(category) {
      this.categorySelectedItem = category;
      this.productQueryDto.categoryId = category.id;
      // 查询对应的商品分类下面的商品数据
      this.fetchFreshData();
    },
    /**
     * 加载商品类别数据
     */
    fetchCategoryList() {
      this.$axios
        .post("/category/query", {})
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.categoryList = data.data;
            this.isUseCategoryList = data.data.filter(
              category => category.isUse
            );
            this.isUseCategoryList.unshift({ id: null, name: "全部" });
            this.categorySelected(this.isUseCategoryList[0]);
          }
        })
        .catch(error => {
          console.log("商品类别查询异常：", error);
        });
    }
  }
};
</script>
<style scoped lang="scss">
.cover {
  img {
    width: 100%;
    height: 160px;
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

.info {
  display: flex;
  justify-content: left;
  align-items: center;
  gap: 4px;

  img {
    width: 20px;
    height: 20px;
    border-radius: 50%;
  }

  span {
    font-size: 14px;
    color: #999;
  }
}

.bargain {
  display: inline-block;
  font-size: 12px;
  background-color: rgb(246, 246, 246);
  line-height: 24px;
  padding-inline: 10px;
  padding-block: 4px;
  margin-right: 5px;
  border-radius: 5px;
  cursor: pointer;

  span {
    display: inline-block;
    padding-inline: 10px;
    border-radius: 5px;
  }
}

.product-list {
  padding-block: 20px;

  .item-product {
    padding: 10px 10px 16px 10px;
    box-sizing: border-box;
    border-radius: 15px;
    transition: all 0.5s;
    cursor: pointer;
  }

  .item-product:hover {
    box-shadow: 1px 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.06);
  }
}

.nav-category {
  margin-block: 10px;
  display: flex;
  justify-content: space-between;

  .left {
    display: flex;
    justify-content: left;
    gap: 6px;

    span {
      display: inline-block;
      background-color: rgb(246, 246, 246);
      padding: 6px 12px;
      cursor: pointer;
      border-radius: 15px;
      font-size: 12px;
    }

    span:hover {
      background-color: rgb(242, 242, 242);
    }
  }
}
</style>
