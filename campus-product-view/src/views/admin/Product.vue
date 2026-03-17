<template>
  <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
    <el-row style="padding: 10px;margin-left: 5px;">
      <el-row>
        <span class="bargain">
          <span
            :style="{
              backgroundColor:
                bargainSelectedItem.name === bargain.name
                  ? 'rgb(255,255,255)'
                  : ''
            }"
            @click="bargainSelected(bargain)"
            v-for="(bargain, index) in bargainStatus"
            :key="index"
            >{{ bargain.name }}</span
          >
        </span>
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
        <el-input
          size="small"
          style="width: 166px;"
          v-model="productQueryDto.name"
          placeholder="商品名"
          clearable
          @clear="handleFilterClear"
        >
          <el-button
            slot="append"
            @click="handleFilter"
            icon="el-icon-search"
          ></el-button>
        </el-input>
      </el-row>
    </el-row>
    <el-row style="margin: 0 22px;border-top: 1px solid rgb(245,245,245);">
      <el-table :stripe="true" :data="tableData">
        <el-table-column prop="userAvatar" width="68" label="头像">
          <template slot-scope="scope">
            <el-avatar
              :size="25"
              :src="scope.row.userAvatar"
              style="margin-top: 10px;"
            ></el-avatar>
          </template>
        </el-table-column>
        <el-table-column
          prop="userName"
          width="180"
          label="用户"
        ></el-table-column>
        <el-table-column prop="name" label="商品名"></el-table-column>
        <el-table-column
          prop="categoryName"
          width="110"
          label="类别"
        ></el-table-column>
        <el-table-column prop="price" width="130" label="价格">
          <template slot-scope="scope">
            <span>{{ scope.row.price }}元</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="inventory"
          width="110"
          label="库存"
        ></el-table-column>
        <el-table-column prop="oldLevel" width="168" label="新旧程度">
          <template slot-scope="scope">
            <span>{{ scope.row.oldLevel }}成新</span>
          </template>
        </el-table-column>
        <el-table-column prop="isBargain" width="138" label="是否支持打折">
          <template slot-scope="scope">
            <span>{{ scope.row.isBargain ? "支持打折" : "不支持打折" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          width="168"
          label="发布时间"
        ></el-table-column>
        <el-table-column label="操作" width="160">
          <template slot-scope="scope">
            <span class="text-button" @click="handleEdit(scope.row)"
              >商品详情</span
            >
            <span class="text-button" @click="handleDelete(scope.row)"
              >删除</span
            >
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        style="margin:10px 0;float: right;"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalItems"
      ></el-pagination>
    </el-row>
    <el-drawer
      title="商品详情"
      :visible.sync="drawerProductOperaion"
      :direction="direction"
    >
      <div style="padding: 0 15px;">
        <div>
          <span class="dialog-hover">产品封面图</span>
          <div class="detail-cover">
            <div
              @click="coverExpansion(cover)"
              class="cover"
              v-for="(cover, index) in coverList"
              :key="index"
            >
              <img :src="cover" />
            </div>
          </div>
        </div>
        <div>
          <span class="dialog-hover">商品详情</span>
          <div style="padding: 10px 6px;" v-html="data.detail"></div>
        </div>
      </div>
    </el-drawer>
    <el-dialog :visible.sync="dialogCoverExpansion" width="35%">
      <div
        style="padding: 10px;display: flex;justify-content: center;align-items: center;"
      >
        <img
          style="width: 500px;height: 500px;"
          :src="cover"
          alt=""
          srcset=""
        />
      </div>
    </el-dialog>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      data: {},
      currentPage: 1,
      coverList: [],
      searchTime: [],
      cover: null, // 放大查看的产品图略
      pageSize: 10,
      totalItems: 0,
      drawerProductOperaion: false, // 开关
      isOperation: false, // 开关-标识新增或修改
      tableData: [],
      delectedRows: [],
      dialogCoverExpansion: false,
      productQueryDto: {}, // 搜索条件
      direction: "rtl", // 从右往左 right to left
      categoryList: [],
      bargainSelectedItem: {},
      bargainStatus: [
        { isBargain: null, name: "全部" },
        { isBargain: true, name: "支持砍价" },
        { isBargain: false, name: "不支持砍价" }
      ]
    };
  },
  created() {
    this.fetchCategoryList();
    this.fetchFreshData();
    // 页面加载时，默认不启用砍价查询条件
    this.bargainSelected(this.bargainStatus[0]);
  },
  methods: {
    /**
     * 砍价状态选中事件
     * @param {*} bargain 砍价状态
     */
    bargainSelected(bargain) {
      this.bargainSelectedItem = bargain;
      this.productQueryDto.isBargain = bargain.isBargain;
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
            // push 往尾部添加元素 尾插法
            // unshift 往头部添加元素 头插法
            this.categoryList.unshift({ id: null, name: "全部" });
          }
        })
        .catch(error => {
          console.log("商品类别查询异常：", error);
        });
    },
    /**
     * 产品图放大查看
     * @param {*} cover 图片链接
     */
    coverExpansion(cover) {
      this.cover = cover;
      this.dialogCoverExpansion = true;
    },
    cannel() {
      this.data = {};
      this.drawerProductOperaion = false;
      this.isOperation = false;
    },
    // 批量删除数据
    async batchDelete() {
      if (!this.delectedRows.length) {
        this.$message(`未选中任何数据`);
        return;
      }
      const confirmed = await this.$swalConfirm({
        title: "删除商品数据",
        text: `删除后不可恢复，是否继续？`,
        icon: "warning"
      });
      if (confirmed) {
        try {
          let ids = this.delectedRows.map(entity => entity.id);
          const response = await this.$axios.post(`/product/batchDelete`, ids);
          if (response.data.code === 200) {
            this.$notify({
              duration: 1000,
              title: "信息删除",
              message: "删除成功",
              type: "success"
            });
            this.fetchFreshData();
            return;
          }
        } catch (error) {
          this.$message.error("商品信息删除异常：", error);
          console.error(`商品信息删除异常：`, error);
        }
      }
    },
    /**
     * 商品查询
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
      this.productQueryDto.current = this.currentPage;
      this.productQueryDto.size = this.pageSize;
      this.productQueryDto.startTime = startTime;
      this.productQueryDto.endTime = endTime;
      this.$axios
        .post("/product/query", this.productQueryDto)
        .then(res => {
          const { data } = res; // 解构
          if (data.code === 200) {
            this.tableData = data.data;
            this.totalItems = data.total;
          }
        })
        .catch(error => {
          this.$notify.error({
            title: "查询操作",
            message: error
          });
        });
    },
    add() {
      this.drawerProductOperaion = true;
    },
    handleFilter() {
      this.currentPage = 1;
      this.fetchFreshData();
    },
    handleFilterClear() {
      this.handleFilter();
    },
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.fetchFreshData();
    },
    handleCurrentChange(val) {
      this.currentPage = val;
      this.fetchFreshData();
    },
    parseCoverList(coverList) {
      this.coverList = coverList.split(",");
    },
    handleEdit(row) {
      this.data = row;
      this.parseCoverList(row.coverList);
      this.drawerProductOperaion = true;
    },
    handleDelete(row) {
      this.delectedRows.push(row);
      this.batchDelete();
    }
  }
};
</script>
<style scoped lang="scss">
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

.detail-cover {
  display: flex;
  justify-content: left;
  gap: 10px;

  .cover {
    padding: 10px;
    box-sizing: border-box;
    border-radius: 5px;
    cursor: pointer;

    img {
      width: 100px;
      height: 100px;
    }
  }

  .cover:hover {
    background-color: rgb(246, 246, 246);
  }
}
</style>
