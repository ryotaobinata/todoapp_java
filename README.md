# ✅ Java製 Todoリストアプリケーション

Java で作成したシンプルな Todo リスト管理アプリです。日々のタスクを整理し、進捗を可視化するためのデスクトップアプリケーションです。

---

## 📋 タスクの構成

1つのタスクは以下の要素で構成されています：

- 📝 タイトル（必須）
- 📄 詳細
- 📅 締め切り（`YYYY/MM/DD` 形式）
- ✅ 完了状態（チェックあり/なし）

---

## ✨ 主な機能

- ➕ **タスクの追加**
- ⚙️ **詳細と期限の設定**
- 🔄 **タスクの編集（更新）**
- 🗑️ **タスクの削除（複数選択で一括削除可）**

---

## 🎬 プレビュー

> 🔽 実際の動作の様子はこちら：<br>
[タスクの追加](assets/add_task.gif)<br>
[タスクの詳細](assets/description.gif)<br>
[タスクの削除](remove_task.gif)<br>
（ここに動画やGIF、もしくは YouTube リンクを貼ってください）

---

## 🛠 使用技術

- Java 17+
- JavaFX
- H2 Database（組み込み型）
- Maven

---

## 📁 実行方法

```bash
git clone https://github.com/yourusername/todo-java-app.git
cd todo-java-app
mvn clean javafx:run
