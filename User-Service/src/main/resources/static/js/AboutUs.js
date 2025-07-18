// Sample backend endpoint: /mediaUploads/images and /mediaUploads/docs

window.onload = function () {
  loadMedia("/mediaUploads/images", "imageContainer", true);
  loadMedia("/mediaUploads/docs", "docContainer", false);
};

function loadMedia(endpoint, containerId, isImage) {
  fetch(endpoint)
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById(containerId);
      data.forEach(fileUrl => {
        const element = isImage
          ? document.createElement("img")
          : document.createElement("iframe");

        element.src = fileUrl;
        element.alt = "media";
        element.loading = "lazy";
        container.appendChild(element);
      });
    })
    .catch(err => {
      console.error(`Error loading ${containerId}:`, err);
    });
}
