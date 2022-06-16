<template>
    <div>
        <a-button class="editable-add-btn" style="margin-bottom: 8px" @click="showModal">新增研究领域</a-button>
        <a-modal v-model:visible="visible" title="新增研究领域" @ok="handleAdd">
            <a-form :model="formState" name="basic" autocomplete="off">
                <a-form-item label="名称" name="name" :rules="[{ required: true, message: '请输入名称' }]">
                    <a-input v-model:value="formState.name" />
                </a-form-item>

                <a-form-item label="描述" name="description" :rules="[{ required: true, message: '请输入相应描述!' }]">
                    <a-textarea v-model:value="formState.description" auto-size />
                </a-form-item>
            </a-form>
        </a-modal>
    </div>
    <a-table :columns="columns" :data-source="dataSource" bordered>
        <template #bodyCell="{ column, text, record }">
            <template v-if="'name' == column.dataIndex">
                <div>
                    <a-input v-if="editableData[record.id]" v-model:value="editableData[record.id][column.dataIndex]" />
                    <template v-else>
                        {{ text }}
                    </template>
                </div>
            </template>
            <template v-if="'description' == column.dataIndex">
                <div>
                    <a-textarea auto-size v-if="editableData[record.id]"
                        v-model:value="editableData[record.id][column.dataIndex]" />
                    <template v-else>
                        {{ text }}
                    </template>
                </div>
            </template>
            <template v-else-if="column.dataIndex === 'operation'">
                <div class="editable-row-operations">
                    <span v-if="editableData[record.id]">
                        <a-typography-link @click="save(record.id)">保存</a-typography-link>
                        <a-divider type="vertical" />
                        <a-typography-link @click="cancel(record.id)">取消</a-typography-link>
                    </span>
                    <span v-else>
                        <a @click="edit(record.id)">编辑</a>
                    </span>
                    <a-divider type="vertical" />
                    <a-popconfirm title="是否要删除该方向?" ok-text="Yes" cancel-text="No"
                        @confirm="deleteExpertise(record.id)" @cancel="cancel">
                        <a href="#">删除</a>
                    </a-popconfirm>
                </div>
            </template>
        </template>
    </a-table>
</template>

<script setup lang="ts">
import { cloneDeep } from 'lodash-es';
import { reactive, ref, UnwrapRef, Ref } from 'vue';
import { GetExpertisesApi, UpdateExpertiseApi, DeleteExpertiseApi, AddExpertiseApi } from '@/request/api'
import { message } from 'ant-design-vue';

const visible = ref<boolean>(false);
const showModal = () => {
    visible.value = true;
};

const columns = [
    {
        title: '编号',
        dataIndex: 'id',
        width: '5%'
    },
    {
        title: '名字',
        dataIndex: 'name',
        width: '6%'
    },
    {
        title: '描述',
        dataIndex: 'description',
    },
    {
        title: '操作',
        dataIndex: 'operation',
        width: '15%',
    },
];
interface DataItem {
    id: number;
    name: string;
    description: string;
}

const formState = reactive<DataItem>({
    id: 0,
    name: '',
    description: '',
});
const data: Ref<DataItem[]> = ref([]);
const getExpertises = () => {
    GetExpertisesApi().then((res: any) => {
        if (res.errno === 0) {
            data.value = res.data['expertises'];
        }
    })
}
getExpertises()

const dataSource = ref(data);
const editableData: UnwrapRef<Record<number, DataItem>> = reactive({});

const edit = (key: number) => {
    editableData[key] = cloneDeep(dataSource.value.filter(item => key === item.id)[0]);
};
const save = (key: number) => {
    const token = localStorage.getItem('token');
    if (token) {
        UpdateExpertiseApi(token, editableData[key]['id'], editableData[key]['name'], editableData[key]['description']).then((res: any) => {
            if (res.errno === 0) {
                Object.assign(dataSource.value.filter(item => key === item.id)[0], editableData[key]);
                delete editableData[key];
            }
        })
    }
};
const cancel = (key: number) => {
    delete editableData[key];
};
const deleteExpertise = (key: number) => {
    const token = localStorage.getItem('token');
    if (token) {
        DeleteExpertiseApi(token, key).then((res: any) => {
            if (res.errno === 0) {
                dataSource.value = dataSource.value.filter(item => key !== item.id)
                message.info("删除成功！")
            }
        })
    }
};
const handleAdd = () => {
    if (formState.name === '') { message.error("请输入名称！"); return; }
    if (formState.description === '') { message.error("请输入相应描述！"); return; }
    const token = localStorage.getItem('token');
    if (token) {
        AddExpertiseApi(token, formState.name, formState.description).then((res: any) => {
            if (res.errno === 0) {
                message.info("添加成功！");
                getExpertises();
                formState.name = '';
                formState.description = '';
                visible.value = false;
            }
        })
    }
};
</script>

<style scoped lang="scss">
.editable-row-operations a {
    margin-right: 8px;
}
</style>