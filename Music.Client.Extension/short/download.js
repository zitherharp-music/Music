function download(url, name) {
    var link = document.createElement("a");
    link.download = name;
    link.href = url;
    link.click();
    window.close();
}

download(window.location.href, 'zhmshort');