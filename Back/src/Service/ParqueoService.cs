using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Model;
using Model.DTOs;
using Persistence.Database;
using Service.Commons;
using Service.Extensions;
using System.Linq;
using System.Threading.Tasks;

namespace Service
{
    public interface IParqueoService
    {
        Task<DataCollection<ParqueoDto>> GetAll(int page, int take);
        Task<ParqueoDto> GetById(int id);
        Task<ParqueoDto> Create(ParqueoCreateDto model);
        Task Update(int id, ParqueoUpdateDto model);
        Task Remove(int id);

        Task<DataCollection<ParqueoDto>> GetAllDistrito(int distritoId, int page, int take);
    }

    public class ParqueoService : IParqueoService
    {
        private readonly ApplicationDbContext _context;
        private readonly IMapper _mapper;

        public ParqueoService(
            ApplicationDbContext context,
            IMapper mapper
        )
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<DataCollection<ParqueoDto>> GetAll(int page, int take)
        {
            return _mapper.Map<DataCollection<ParqueoDto>>(
                await _context.Parqueos.OrderByDescending(x => x.Name)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }

        public async Task<ParqueoDto> GetById(int id)
        {
            return _mapper.Map<ParqueoDto>(
                await _context.Parqueos.SingleAsync(x => x.Id == id)
            );
        }

        public async Task<ParqueoDto> Create(ParqueoCreateDto model)
        {
            var entry = new Parqueo
            {
                Name = model.Name,
                Description = model.Description,
                Price = model.Price,
                Contact = model.Contact,
                Address = model.Address,
                Latitude = model.Latitude,
                Longitude = model.Longitude,
                UrlImg = "",
                DistritoId = model.DistritoId,
                Enable = model.Enable
            };

            await _context.AddAsync(entry);
            await _context.SaveChangesAsync();

            return _mapper.Map<ParqueoDto>(entry);
        }

        public async Task Update(int id, ParqueoUpdateDto model)
        {
            var entry = await _context.Parqueos.SingleAsync(x => x.Id == id);

            entry.Name = model.Name;
            entry.Description = model.Description;
            entry.Price = model.Price;
            entry.Contact = model.Contact;
            entry.Address = model.Address;
            entry.Latitude = model.Latitude;
            entry.Longitude = model.Longitude;
            
            entry.DistritoId = model.DistritoId;
            entry.Enable = model.Enable;

            await _context.SaveChangesAsync();
        }

        public async Task Remove(int id)
        {
            //eliminacion fisica
            /*_context.Remove(new Parqueo
            {
                Id = id
            });*/

            //eliminacion logica
            var entry = await _context.Parqueos.SingleAsync(x => x.Id == id);
            entry.Enable = false;

            await _context.SaveChangesAsync();
        }


        public async Task<DataCollection<ParqueoDto>> GetAllDistrito(int distritoId, int page, int take)
        {
            int iddd = distritoId;
            return _mapper.Map<DataCollection<ParqueoDto>>(
                await _context.Parqueos.OrderByDescending(x => x.Id)
                               .Where(y => y.DistritoId == distritoId && y.Enable == true)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }
    }
}
