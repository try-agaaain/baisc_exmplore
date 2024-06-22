#!/bin/bash

# 目标文件夹路径
TARGET_DIR=$1
# 输出文件
OUTPUT_FILE="output.txt"

# 检查是否提供了目标文件夹路径
if [ -z "$TARGET_DIR" ]; then
    echo "请提供目标文件夹路径。"
    exit 1
fi

# 检查目标文件夹是否存在
if [ ! -d "$TARGET_DIR" ]; then
    echo "目标文件夹不存在：$TARGET_DIR"
    exit 1
fi

# 清空或创建输出文件
> "$OUTPUT_FILE"

# 使用find命令查找所有文件，并排除输出文件本身
find "$TARGET_DIR" -type f ! -name "$OUTPUT_FILE" | while read file; do
    # 将完整文件路径添加到输出文件
    echo "// path: $file" >> "$OUTPUT_FILE"
    # 将文件内容添加到输出文件
    cat "$file" >> "$OUTPUT_FILE"
    # 在文件内容之后添加空行作为分隔
    echo "" >> "$OUTPUT_FILE"
done

echo "合并完成。"