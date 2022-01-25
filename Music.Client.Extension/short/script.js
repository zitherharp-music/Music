function renderContent(content) {
    document.getElementById('content').innerHTML = content;
}

async function getVideoDetails() {
    let videoUrl = document.getElementById('video_url').value;
    let components = videoUrl.split('/');
    let videoId = components[components.length - 1];
    let fetchUrl = 'https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=' + videoId;
    let result = await fetch(fetchUrl);
    let jsonResult = await result.json();
    return jsonResult.item_list[0];
}

document.getElementById('download_video').addEventListener('click', async () => {
    var item = await getVideoDetails();
    var authorName = item.author.nickname;
    var description = item.desc;
    var videoId = item.video.vid;
    var url = "https://www.douyin.com/aweme/v1/play/?video_id=" + videoId;
    renderContent('<p><b>Author: </b>' + authorName + '</p>' +
        '<p><b>Description: </b>' + description + '</p>');
    open(url);
});

