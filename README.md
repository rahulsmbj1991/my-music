# my-music ---  music informations
------

To build the application, run the following command.

```
$ mvn clean install
```

## To Run the application 

Run MyMusicApplication.java

As we are using spring boot so we don`t need to run/mantain any server explicitly, spring boot has its own tomcat server inbuilt runs on 8080 port by default.If we want run this application on different port we just need to define below in the application.properties file.

```
server.port = 8081 or something else
```
## End Point URL [GET Request]

```
http://localhost:8080/music/mbid/f27ec8db-af05-4f36-916e-3d57f91ecf5e
```

-- Response

```
{
	"payload": {
		"mbid": "\"f27ec8db-af05-4f36-916e-3d57f91ecf5e\"",
		"description": "\"American singer, dancer, entertainer, songwriter, producer and recording artist.\\r\\n\\r\\nBorn: 29 August 1958 in Gary, Indiana, USA. \\r\\nDied: 25 June 2009 in Los Angeles, California, USA (aged 50). \\r\\n\\r\\nKnown affectionately as the \\\"King Of Pop\\\", Jackson was a singer, dancer, musician, music producer, writer, entertainer, singer-songwriter, choreographer, record producer, recording artist, poet, arranger, businessman, philanthropist, actor, voice artist, and comedian. \\r\\n \\r\\nJackson began his career as the youngest member of [a=The Jackson 5] and started his solo recording career in 1971. Brother of recording artists [a=Jackie Jackson], [a=Janet Jackson], [a=Jermaine Jackson], [a=La Toya Jackson], [a=Marlon Jackson], [a=Randy Jackson], [a=Rebbie Jackson] & [a=Tito Jackson], as well as uncle of [a=3T].\\r\\n\\r\\nInducted into Rock And Roll Hall of Fame in 2001 (as performer).\\r\\n\\r\\nOn June 25, 2009, Michael Jackson died of acute propofol and benzodiazepine intoxication at his home on North Carolwood Drive in the Holmby Hills neighborhood of Los Angeles, CA. His personal physician, Conrad Murray, said he had found Jackson in his room, not breathing and with a barely detectable pulse, and that he administered CPR on Jackson to no avail. After a call was placed to 9-1-1 at 12:21 p.m., Jackson was treated by paramedics at the scene and was later pronounced dead at the Ronald Reagan UCLA Medical Center.\"",
		"albums": [
			{
				"title": "\"One Day in Your Life\"",
				"id": "0621bd78-b867-39ab-8606-9636bfd94447",
				"image": "http://coverartarchive.org/release/264039fe-1253-4aa5-baae-406163c733b5/1395290002.jpg"
			},
			{
				"title": "\"Michael\"",
				"id": "0656d7fa-0ed7-4d3f-887e-ed728697c5ad",
				"image": "http://coverartarchive.org/release/ff783a99-7ec5-3c1e-866e-71f286d61759/1613934407.jpg"
			},
			{
				"title": "\"Music & Me\"",
				"id": "06b064b9-01e7-32d8-b585-86404584e795",
				"image": "http://coverartarchive.org/release/7c73f72d-8fa2-45a7-9125-a04696f64f3a/1620517729.jpg"
			},
			{
				"title": "\"14 Greatest Hits With the Jackson 5\"",
				"id": "22fc8d45-6802-46f8-8a78-6ae823a9ed92",
				"image": "http://coverartarchive.org/release/f1c43fd5-066d-462a-93fa-33d7bb23564d/1613886666.jpg"
			},
			{
				"title": "\"Anthology\"",
				"id": "37906983-1005-36fb-b8e7-3a04687e6f4f",
				"image": "http://coverartarchive.org/release/a7a74484-8c25-47e3-9afc-7de701ad3dde/1619836290.jpg"
			},
			{
				"title": "\"The Complete Remix Suite\"",
				"id": "3c3d0a5c-8bcd-4416-a213-2ac899a53444",
				"image": "http://coverartarchive.org/release/89737f7e-7454-4753-b918-a6b4e759813c/17417517721.jpg"
			}
		]
	},
	"message": "data found",
	"status": 200
}
```
