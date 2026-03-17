<template>
  <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
    <el-row style="padding: 10px;margin-left: 5px;">
      <el-row>
        <el-date-picker
          style="width: 216px;margin-right: 5px;"
          @change="fetchFreshData"
          size="small"
          v-model="searchTime"
          type="daterange"
          range-separator="至"
          start-placeholder="发送开始"
          end-placeholder="发送结束"
        >
        </el-date-picker>
        <el-input
          size="small"
          style="width: 166px;"
          v-model="messageQueryDto.content"
          placeholder="消息体"
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
      <el-table :stripe="true" :data="tableData" style="width: 100%">
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
          width="130"
          label="接收者"
        ></el-table-column>
        <el-table-column prop="content" label="消息体"></el-table-column>
        <el-table-column prop="isRead" width="168" label="是否已读">
          <template slot-scope="scope">
            <span>{{ scope.row.isRead ? "已读" : "未读" }}</span>
          </template>
        </el-table-column>
        <el-table-column
          prop="createTime"
          width="200"
          label="发送时间"
        ></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
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
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      tableData: [],
      delectedRows: [],
      searchTime: [],
      messageQueryDto: {} // 搜索条件
    };
  },
  created() {
    this.fetchFreshData();
  },
  methods: {
    // 批量删除数据
    async batchDelete() {
      if (!this.delectedRows.length) {
        this.$message(`未选中任何数据`);
        return;
      }
      const confirmed = await this.$swalConfirm({
        title: "删除消息数据",
        text: `删除后不可恢复，是否继续？`,
        icon: "warning"
      });
      if (confirmed) {
        try {
          let ids = this.delectedRows.map(entity => entity.id);
          const response = await this.$axios.post(`/message/batchDelete`, ids);
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
          this.$message.error("消息信息删除异常：", error);
          console.error(`消息信息删除异常：`, error);
        }
      }
    },
    /**
     * 消息查询
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
      this.messageQueryDto.current = this.currentPage;
      this.messageQueryDto.size = this.pageSize;
      this.messageQueryDto.startTime = startTime;
      this.messageQueryDto.endTime = endTime;
      this.$axios
        .post("/message/query", this.messageQueryDto)
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
    handleDelete(row) {
      this.delectedRows.push(row);
      this.batchDelete();
    }
  }
};
</script>
<style scoped lang="scss"></style>
