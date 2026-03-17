<template>
  <el-row style="background-color: #FFFFFF;padding: 5px 0;border-radius: 5px;">
    <el-row style="padding: 10px;margin-left: 5px;">
      <el-row>
        <el-input
          size="small"
          style="width: 166px;"
          v-model="operationLogQueryDto.detail"
          placeholder="行为"
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
        <el-table-column
          prop="userAccount"
          width="168"
          label="用户账号"
        ></el-table-column>
        <el-table-column
          prop="userName"
          width="228"
          label="用户名"
        ></el-table-column>
        <el-table-column prop="detail" label="操作"></el-table-column>
        <el-table-column
          prop="createTime"
          width="208"
          :sortable="true"
          label="操作时间"
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
      data: {},
      currentPage: 1,
      pageSize: 10,
      totalItems: 0,
      tableData: [],
      delectedRows: [],
      operationLogQueryDto: {} // 搜索条件
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
        title: "删除操作日志数据",
        text: `删除后不可恢复，是否继续？`,
        icon: "warning"
      });
      if (confirmed) {
        try {
          let ids = this.delectedRows.map(entity => entity.id);
          const response = await this.$axios.post(
            `/operation-log/batchDelete`,
            ids
          );
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
          this.$message.error("操作日志信息删除异常：", error);
          console.error(`操作日志信息删除异常：`, error);
        }
      }
    },
    /**
     * 操作日志查询
     */
    fetchFreshData() {
      this.operationLogQueryDto.current = this.currentPage;
      this.operationLogQueryDto.size = this.pageSize;
      this.$axios
        .post("/operation-log/query", this.operationLogQueryDto)
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
