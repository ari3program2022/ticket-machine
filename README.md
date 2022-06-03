# ticket-machine
LINE上で整理券を発行できるウェブアプリケーションです。

# 制作背景
ポートフォリオとして作成しており、商用利用の目的はございません。
本作品を通して、以下の技術スタックの証明いたします。

## [使用技術]
- spring boot、Javaの基礎知識
- SQLを含めた、DBの基礎知識
- web APIの基本知識
- SDKを使った開発
- Gitを使ったチーム開発
- Github issueの活用
- AWSをつかったアプリケーションの構築（VPC,EC2,ALB,RDS,Cloud Formationなどを利用）

# システム構成
## AWS本番構成(費用がかかるため、現在停止中)
- クラウドフォーメーションで作成しました。テンプレートファイルは(ticket-machine/aws)にございます。
- 管理者画面の機能は、現在未実装です。
<img width="749" alt="image" src="https://user-images.githubusercontent.com/105266080/171902192-1a3cac9c-9b3d-4fa5-baec-6f9e0abfe258.png">

## Heroku開発環境
<img width="575" alt="image" src="https://user-images.githubusercontent.com/105266080/171443731-10f1b9c4-d0a2-4627-8def-8a07b364a54d.png">

# 設計
## 画面遷移図
<img width="758" alt="image" src="https://user-images.githubusercontent.com/105266080/171441374-18108335-ab3e-4d67-b8ea-7796795acd67.png">

## フロー
### 発券処理
<img width="602" alt="image" src="https://user-images.githubusercontent.com/105266080/171423860-57304a53-730e-4d08-945a-330df158508c.png">

### 待ち時間確認
<img width="589" alt="image" src="https://user-images.githubusercontent.com/105266080/171424075-39fd12da-7e14-4fcd-a3af-c7977e00f32d.png">

### キャンセル処理
<img width="618" alt="image" src="https://user-images.githubusercontent.com/105266080/171424299-14133cab-8a9d-4070-ab85-6734bc2d3ab9.png">

## テーブル相関図
<img width="496" alt="image" src="https://user-images.githubusercontent.com/105266080/171902693-6ac3657c-93c6-444f-ae8a-b3c5c1333cf1.png">

# LINEBotアカウント
## Heroku開発環境
@746yzuom
![image](https://user-images.githubusercontent.com/105266080/171437205-99ecd61d-88f6-406b-925f-9e94a98e882b.png)

