package com.example.layoutcjj

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 任务数据类
data class Task(
    val name: String,
    val isCompleted: Boolean = false
)

@Composable
fun LearnTaskScreen() {
    // 初始状态：3项任务，完成1项
    val taskList = remember {
        mutableStateListOf(
            Task("学习 Column 和 Row", isCompleted = true),
            Task("学习状态管理", isCompleted = false),
            Task("完成 Compose 实验", isCompleted = false)
        )
    }
    var inputText by remember { mutableStateOf("") }
    // 统计已完成数量
    val completedCount = taskList.count { it.isCompleted }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // 页面标题
        Text(
            text = "课程学习任务",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFB71C1C)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 输入框 + 添加按钮：圆角10dp、高度50dp、文字居中
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                placeholder = {
                    Text(
                        text = "请输入学习任务",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFBDBDBD)
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                textStyle = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        taskList.add(Task(inputText))
                        inputText = ""
                    }
                },
                modifier = Modifier.height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB71C1C)
                )
            ) {
                Text(
                    text = "添加",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 完成进度统计
        Text(text = "已完成: $completedCount / ${taskList.size}")

        Spacer(modifier = Modifier.height(12.dp))

        // 任务列表：每项纯白背景 + 极淡灰色边框 + 圆角4dp
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 核心修复：用索引遍历，替换元素触发状态更新
            itemsIndexed(taskList, key = { _, task -> task.name }) { index, task ->
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(4.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E5E5)),
                    color = Color(0xFFFFFFFF)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 红色勾选框 - 修复点击切换逻辑
                        Checkbox(
                            checked = task.isCompleted,
                            onCheckedChange = { isChecked ->
                                // 替换列表元素，触发状态重组
                                taskList[index] = task.copy(isCompleted = isChecked)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFB71C1C),
                                uncheckedColor = Color.Gray
                            )
                        )

                        // 任务文字：完成后变灰+删除线
                        Text(
                            text = task.name,
                            modifier = Modifier.weight(1f),
                            color = if (task.isCompleted) Color.Gray else Color.Black,
                            textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                        )

                        // 红色文字删除按钮
                        TextButton(
                            onClick = {
                                taskList.removeAt(index)
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFFE53935)
                            )
                        ) {
                            Text("删除")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "任务列表界面")
@Composable
fun PreviewLearnTask() {
    LearnTaskScreen()
}