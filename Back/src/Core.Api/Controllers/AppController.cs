using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Model.DTOs;
using Service;
using Service.Commons;
using System;
using System.IO;
using System.Threading;
using System.Threading.Tasks;

namespace Core.Api.Controllers
{
    [ApiController]
    [Route("app")]
    public class AppController : ControllerBase
    {
        private readonly IParqueoService _parqueoService;
        private readonly IDistritoService _distritoService;

        public AppController(IParqueoService ParqueoService, IDistritoService DistritoService)
        {
            _parqueoService = ParqueoService;
            _distritoService = DistritoService;
        }

        [HttpGet("parqueos")]
        public async Task<ActionResult<DataCollection<ParqueoDto>>> GetAllDistrito(int distritoId, int page = 1, int take = 20)
        {
            return await _parqueoService.GetAllDistrito(distritoId, page, take);
        }

        [HttpGet("distritos")]
        public async Task<ActionResult<DataCollection<DistritoDto>>> GetAvailables(int page = 1, int take = 50)
        {
            return await _distritoService.GetAvailables(page, take);
        }








        //Upload Img
        [HttpPost("subirplaca", Name = "subirplaca")]
        [ProducesResponseType(StatusCodes.Status200OK)]
        [ProducesResponseType(typeof(string), StatusCodes.Status400BadRequest)]
        public async Task<IActionResult> UploadFile(IFormFile file, CancellationToken cancellationToken)
        {
            if (CheckIfImageFile(file))
            {
                await WriteFile(file);
            }
            else
            {
                return BadRequest(new { message = "Invalid file extension" });
            }

            return Ok();
        }

        private bool CheckIfImageFile(IFormFile file)
        {
            var extension = "." + file.FileName.Split('.')[file.FileName.Split('.').Length - 1];
            return (extension == ".jpg" || extension == ".png"); // Change the extension based on your need
        }

        private async Task<bool> WriteFile(IFormFile file)
        {
            bool isSaveSuccess = false;
            string fileName;
            try
            {
                var extension = "." + file.FileName.Split('.')[file.FileName.Split('.').Length - 1];
                fileName = DateTime.Now.Ticks + extension; //Create a new Name for the file due to security reasons.

                var pathBuilt = Path.Combine(Directory.GetCurrentDirectory(), "Upload\\placas");

                if (!Directory.Exists(pathBuilt))
                {
                    Directory.CreateDirectory(pathBuilt);
                }

                var path = Path.Combine(Directory.GetCurrentDirectory(), "Upload\\placas", fileName);

                using (var stream = new FileStream(path, FileMode.Create))
                {
                    await file.CopyToAsync(stream);
                }

                isSaveSuccess = true;
            }
            catch (Exception e)
            {
                //log error
            }

            return isSaveSuccess;
        }

    }
}
