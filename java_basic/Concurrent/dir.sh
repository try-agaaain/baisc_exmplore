#!/bin/bash

# 检查是否提供了输入文件参数
if [ $# -ne 1 ]; then
    echo "Usage: $0 <input_file.txt>"
    exit 1
fi

input_file="$1"
current_file=""
create_file=false

# 逐行读取输入文件
while IFS= read -r line; do
    # 检查是否是文件路径行（以 # 开头）
    if [[ $line =~ ^#[[:space:]]*(.+)$ ]]; then
        # 提取文件路径
        filepath="${BASH_REMATCH[1]}"
        # 创建目录
        mkdir -p "$(dirname "$filepath")"
        # 创建空文件
        > "$filepath"
        current_file="$filepath"
        create_file=true
        continue
    fi
    
    # 如果已经找到文件路径，将内容写入对应文件
    if [ "$create_file" = true ]; then
        echo "$line" >> "$current_file"
    fi
done < "$input_file"

echo "Files have been created successfully!"