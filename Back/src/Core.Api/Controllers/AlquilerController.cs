using Core.Api.Commons;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Model.DTOs;
using Model.Identity;
using Service;
using Service.Commons;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;

/// <summary>
/// Upgrade.pe
/// Developer: Luis Choccechanca
/// Date: 30/01/2022
/// </summary>
namespace Core.Api.Controllers
{
    [Authorize]
    [ApiController]
    [Route("alquileres")]
    public class AlquilerController : ControllerBase
    {
        private readonly IAlquilerService _alquilerService;
        private readonly UserManager<ApplicationUser> _userManager;

        public AlquilerController(
             UserManager<ApplicationUser> userManager, 
             IAlquilerService AlquilerService
        )
        {
            _userManager = userManager;
            _alquilerService = AlquilerService;
        }

        [HttpGet]
        public async Task<ActionResult<DataCollection<AlquilerDto>>> GetAll(int page = 1, int take = 20)
        {
            return await _alquilerService.GetAll(page, take);
        }

        // Ex: Alquilers/1
        [HttpGet("{id}")]
        public async Task<ActionResult<AlquilerDto>> GetById(int id)
        {
            return await _alquilerService.GetById(id);
        }

        [HttpPost]
        public async Task<ActionResult> Create(AlquilerCreateDto model)
        {
            // set UserId
            var userId = User.Claims.Where(x =>
                x.Type.Equals(ClaimTypes.NameIdentifier)
            ).Single().Value;

            var user = await _userManager.FindByIdAsync(userId);

            model.UserId = user.Id;
            // fin set UserId
            
            var result = await _alquilerService.Create(model);
            
            return CreatedAtAction(
                "GetById",
                new { id = result.Id },
                result
            );
        }

        [HttpPut("{id}")]
        public async Task<ActionResult> Update(int id, AlquilerUpdateDto model)
        {
            await _alquilerService.Update(id, model);
            //return NoContent();
            return Ok(new
            {
                code = 1,
                status = "Ok",
                msg = "Registro actualizado."
            });
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult> Remove(int id)
        {
            await _alquilerService.Remove(id);
            //return NoContent();
            return Ok(new
            {
                code = 1,
                status = "Ok",
                msg = "Registro eliminado."
            });
        }

        [HttpGet("deusuario")]
        public async Task<ActionResult<DataCollection<AlquilerDto>>> GetPorUsuario(int page = 1, int take = 20)
        {
            var userId = User.Claims.Where(x =>
                x.Type.Equals(ClaimTypes.NameIdentifier)
            ).Single().Value;
            return await _alquilerService.GetPorUsuario(userId, page, take);
        }

    }
}
